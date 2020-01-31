package com.ioproject.pocketmoney.controller;

import com.ioproject.pocketmoney.entities.EntityChild;
import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.entitiesDTO.ChildGetDTO;
import com.ioproject.pocketmoney.entitiesDTO.ChildPostDTO;
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

    @Autowired
    private ServiceUser serviceUser;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ServiceChild serviceChild;

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

    //TODO::
    @PutMapping("/editChild")
    public ResponseEntity<?> editChild(ChildPostDTO child){
        Optional<EntityUser> user = getCurrentUserFromToken();
        if(user.isPresent()){

        }
        throw(new UnsupportedOperationException("keke reke na meke beke feke;"));
    }

    private List<ChildGetDTO> mapListChildToGetDTO(List<EntityChild> entityChild) {
        List<ChildGetDTO> toReturn = new ArrayList<>();
        //yee Ik it is not really clean XD but gotta go fast XD
        entityChild.forEach(child -> toReturn.add(new ChildGetDTO(child.getId(), child.getUser().getId(), child.getUser().getUsername(),
                child.getPocketMoney(), child.getDescription(), child.getSex(), child.isLivingWithParents(),
                child.getAdministrationUnit().getName(), child.getEducation().getEducationLevel())));
        return toReturn;
    }

    //TODO::
    // add endpoints for: avg pocket money, avg pocket money for city, avg pocket money for edu lvl, list of all kids
    // this should be it? I guess, maybe later add some list of 10 recently added records? dunno


}
