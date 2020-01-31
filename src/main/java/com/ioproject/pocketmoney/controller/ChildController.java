package com.ioproject.pocketmoney.controller;

import com.ioproject.pocketmoney.entities.EntityAdministrationUnit;
import com.ioproject.pocketmoney.entities.EntityChild;
import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.entitiesDTO.AdmUnitAvgMoneyDTO;
import com.ioproject.pocketmoney.entitiesDTO.ChildGetDTO;
import com.ioproject.pocketmoney.entitiesDTO.ChildPostDTO;
import com.ioproject.pocketmoney.service.ServiceAdministrationUnit;
import com.ioproject.pocketmoney.service.ServiceChild;
import com.ioproject.pocketmoney.service.ServiceUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ChildController {

    private final ServiceUser serviceUser;

    private final ModelMapper modelMapper;

    private final ServiceChild serviceChild;

    private final ServiceAdministrationUnit serviceAdministrationUnit;

    public ChildController(ServiceUser serviceUser, ModelMapper modelMapper, ServiceChild serviceChild, ServiceAdministrationUnit serviceAdministrationUnit) {
        this.serviceUser = serviceUser;
        this.modelMapper = modelMapper;
        this.serviceChild = serviceChild;
        this.serviceAdministrationUnit = serviceAdministrationUnit;
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
    public ResponseEntity<ChildPostDTO> editChild(@Valid @RequestBody ChildPostDTO childPostDTO, @PathVariable Long id) {
        Optional<EntityUser> user = getCurrentUserFromToken();
        if (user.isPresent()) {
            Optional<EntityChild> currentChild = serviceChild.get(id);
            //it exist and current user is owner of the record
            if (currentChild.isPresent() && currentChild.get().getUser().getId().equals(user.get().getId())) {
                this.serviceChild.updateChildByDTO(childPostDTO, currentChild.get());
                return ResponseEntity.ok().body(childPostDTO);
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

    @GetMapping("/getAllChildren")
    public ResponseEntity<List<ChildGetDTO>> getAllChildren() {
        return ResponseEntity.ok().body(mapListChildToGetDTO(this.serviceChild.getAll()));
    }

    @GetMapping("/getAverageMoneyAdministrationUnit")
    public ResponseEntity<List<AdmUnitAvgMoneyDTO>> getAverageMoneyAdmUnit() {
        List<EntityAdministrationUnit> administrationUnits = serviceAdministrationUnit.getAll();
        List<AdmUnitAvgMoneyDTO> lstToReturn = new ArrayList<>();
        administrationUnits.forEach(entityAdministrationUnit -> {
            lstToReturn.add(new AdmUnitAvgMoneyDTO(entityAdministrationUnit.getName(), serviceChild.calculateAverageMoneyForAdministrationUnit(entityAdministrationUnit)));
        });
        return ResponseEntity.ok().body(lstToReturn);
    }


    //TODO::
    // add endpoints for: avg pocket money for edu lvl in city, list of all kids for choosen city and edu lvl


}
