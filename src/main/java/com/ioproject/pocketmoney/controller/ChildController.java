package com.ioproject.pocketmoney.controller;

import com.ioproject.pocketmoney.entities.EntityAdministrationUnit;
import com.ioproject.pocketmoney.entities.EntityChild;
import com.ioproject.pocketmoney.entities.EntityEducation;
import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.entitiesDTO.NameFloatForTableDTO;
import com.ioproject.pocketmoney.entitiesDTO.ChildGetDTO;
import com.ioproject.pocketmoney.entitiesDTO.ChildPostDTO;
import com.ioproject.pocketmoney.service.ServiceAdministrationUnit;
import com.ioproject.pocketmoney.service.ServiceChild;
import com.ioproject.pocketmoney.service.ServiceEducation;
import com.ioproject.pocketmoney.service.ServiceUser;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ChildController {

    private final ServiceUser serviceUser;

    private final ModelMapper modelMapper;

    private final ServiceChild serviceChild;

    private final ServiceAdministrationUnit serviceAdministrationUnit;
    private final ServiceEducation serviceEducation;

    public ChildController(ServiceUser serviceUser, ModelMapper modelMapper, ServiceChild serviceChild, ServiceAdministrationUnit serviceAdministrationUnit, ServiceEducation serviceEducation) {
        this.serviceUser = serviceUser;
        this.modelMapper = modelMapper;
        this.serviceChild = serviceChild;
        this.serviceAdministrationUnit = serviceAdministrationUnit;
        this.serviceEducation = serviceEducation;
    }

    private Optional<EntityUser> getCurrentUserFromToken() {
        return serviceUser.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /**
     * Works for current logged user, only for authenticated
     *
     * @param child DTO for Child record
     * @return given child record if correct values
     */
    @PostMapping("/addChild")
    public ResponseEntity<ChildGetDTO> addChild(@Valid @RequestBody ChildPostDTO child) {
        Optional<EntityUser> user = getCurrentUserFromToken();
        //should always return current user or just not allowed to get this mapping but just to be sure:d + check if all
        //required child data are present in JSON
        if (!child.doesntContainAllRequiredValues() && user.isPresent()) {
            Optional<EntityChild> result = serviceChild.saveChildByPostDTO(child, user.get());
            ChildGetDTO toReturn = modelMapper.map(child, ChildGetDTO.class);
            //TODO:: dunno if it is required (username and id)
            toReturn.setUser(user.get().getUsername());
            toReturn.setUserId(user.get().getId());
            //
            if (result.isPresent()) {
                toReturn.setId(result.get().getId());
                return ResponseEntity.ok(toReturn);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * @return list of current user kids
     */
    @GetMapping("/getMyChildren")
    public ResponseEntity<List<ChildGetDTO>> getOwnChildren() {
        Optional<EntityUser> user = getCurrentUserFromToken();
        if (user.isPresent()) {
            List<EntityChild> lstChildren = serviceChild.getAllByUser(user.get());
            return ResponseEntity.ok().body(mapListChildToGetDTO(lstChildren));
        }
        return ResponseEntity.badRequest().build();
    }

    //TODO:: not really true tbh but its oke

    /**
     * returns all values that were edited (that were passed and available)
     *
     * @param childPostDTO child data to change
     * @param id           id of child to change
     * @return all changed values
     */
    @PutMapping("/editChild/{id}")
    public ResponseEntity<?> editChild(@Valid @RequestBody ChildPostDTO childPostDTO, @PathVariable Long id) {
        Optional<EntityUser> user = getCurrentUserFromToken();
        if (user.isPresent()) {
            Optional<EntityChild> currentChild = serviceChild.get(id);
            //it exist and current user is owner of the record
            if (currentChild.isPresent() && currentChild.get().getUser().getId().equals(user.get().getId()) && !childPostDTO.notCorrectPocketMoneyValue()) {
                this.serviceChild.updateChildByDTO(childPostDTO, currentChild.get());
                return ResponseEntity.ok().body("OK child has been edited");
            }
        }
        return ResponseEntity.badRequest().build();
    }

    private List<ChildGetDTO> mapListChildToGetDTO(List<EntityChild> entityChild) {
        List<ChildGetDTO> toReturn = new ArrayList<>();
        //yee Ik it is not really clean XD but gotta go fast XD
        entityChild.forEach(child -> toReturn.add(new ChildGetDTO(child.getId(), child.getUser().getId(), child.getUser().getUsername(),
                child.getPocketMoney(), child.getDescription(), child.getSex(), child.isLivingWithParents(),
                child.getAdministrationUnit().getName(), child.getEducation().getEducationLevel())));
        return toReturn;
    }

    /**
     * @return all children records
     */
    @GetMapping("/getAllChildren")
    public ResponseEntity<List<ChildGetDTO>> getAllChildren() {
        return ResponseEntity.ok().body(mapListChildToGetDTO(this.serviceChild.getAll()));
    }

    /**
     * Call method to get average child pocket money for all records
     *
     * @return average money for all child records
     */
    @GetMapping("/getAveragePocketMoney")
    public ResponseEntity<Float> getAveragePocketMoney() {
        return ResponseEntity.ok().body(serviceChild.calculateAverageMoney());
    }

    /**
     * Call method to get average child pocket money for each city/province
     *
     * @return json with name(city/province name), value(average pocket money for city)
     */
    @GetMapping("/getStatisticsAverage")
    public ResponseEntity<List<NameFloatForTableDTO>> getStatisticsAverage() {
        List<EntityAdministrationUnit> administrationUnits = serviceAdministrationUnit.getAll();
        List<NameFloatForTableDTO> lstToReturn = new ArrayList<>();
        administrationUnits.forEach(entityAdministrationUnit -> lstToReturn.add(new NameFloatForTableDTO(entityAdministrationUnit.getName(),
                serviceChild.calculateAverageMoney(entityAdministrationUnit))));
        return ResponseEntity.ok().body(lstToReturn);
    }

    /**
     * Call method to get average child pocket money for each education level and given city/province
     *
     * @param administrationUnit city/province
     * @return json with name(education lvl), value(average pocket money for given citya and education lvl)
     */
    @GetMapping("/getStatisticsAverage/{administrationUnit}")
    public ResponseEntity<List<NameFloatForTableDTO>> getStatisticsAverage(@PathVariable String administrationUnit) {
        List<EntityEducation> educations = serviceEducation.getAll();
        List<NameFloatForTableDTO> lstToReturn = new ArrayList<>();
        Optional<EntityAdministrationUnit> entityAdministrationUnit = serviceAdministrationUnit.getByName(administrationUnit);
        entityAdministrationUnit.ifPresent(e -> educations.forEach(entityEducation -> lstToReturn.add(new NameFloatForTableDTO(entityEducation.getEducationLevel(),
                serviceChild.calculateAverageMoney(e, entityEducation)))));
        return ResponseEntity.ok().body(lstToReturn);
    }

    /**
     * Call method to get specific children data for given parameters
     *
     * @param administrationUnit city/province
     * @param educationLevel     education name
     * @return all child records for given city and education lvl
     */
    @GetMapping("/getStatisticsAverage/{administrationUnit}/{educationLevel}")
    public ResponseEntity<List<ChildGetDTO>> getStatisticsAverage(@PathVariable String administrationUnit, @PathVariable String educationLevel) {
        Optional<EntityAdministrationUnit> optionalEntityAdministrationUnit = serviceAdministrationUnit.getByName(administrationUnit);
        Optional<EntityEducation> optionalEntityEducation = serviceEducation.getByEducationLevel(educationLevel);
        List<EntityChild> entityChildren = new ArrayList<>();
        optionalEntityAdministrationUnit.ifPresent(entityAdministrationUnit -> optionalEntityEducation.ifPresent(entityEducation -> entityChildren.addAll(
                serviceChild.getAllByAdministrationUnitAndEducation(entityAdministrationUnit, entityEducation))));
        return ResponseEntity.ok().body(mapListChildToGetDTO(entityChildren));
    }

    /**
     * Call method to get average child pocket money for each education level
     *
     * @return json with name(education lvl), value(average pocket money for given education lvl)
     */
    @GetMapping("/getStatisticAverageEducationLvl")
    public ResponseEntity<List<NameFloatForTableDTO>> getAverageMoneyEduLvl() {
        List<EntityEducation> educations = serviceEducation.getAll();
        List<NameFloatForTableDTO> lstToReturn = new ArrayList<>();
        educations.forEach(entityEducation -> lstToReturn.add(new NameFloatForTableDTO(entityEducation.getEducationLevel(), serviceChild.calculateAverageMoney(entityEducation))));
        return ResponseEntity.ok().body(lstToReturn);
    }

    @DeleteMapping("/deleteChild/{id}")
    public ResponseEntity<?> deleteChild(@PathVariable Long id) {
        Optional<EntityChild> child = serviceChild.get(id);
        Optional<EntityUser> user = getCurrentUserFromToken();
        if (child.isPresent() && user.isPresent() && (child.get().getUser().getId().equals(user.get().getId()) || user.get().getUserGroup().getGroupName().equals("ADMIN"))) {
            this.serviceChild.delete(child.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Couldn't delete child");
    }
}
