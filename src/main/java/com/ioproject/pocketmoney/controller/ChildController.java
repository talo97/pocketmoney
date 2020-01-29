package com.ioproject.pocketmoney.controller;

import com.ioproject.pocketmoney.entities.EntityChild;
import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.entitiesDTO.EntityChildPostDTO;
import com.ioproject.pocketmoney.entitiesDTO.EntityUserPostDTO;
import com.ioproject.pocketmoney.service.ServiceChild;
import com.ioproject.pocketmoney.service.ServiceUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    public ResponseEntity<EntityChildPostDTO> addChild(@Valid @RequestBody EntityChildPostDTO child) {
        Optional<EntityUser> user = getCurrentUserFromToken();
        //should always return current user or just not allowed to get this mapping but just to be sure:d + check if all
        //required child data are present in JSON
        if (!child.doesntContainAllRequiredValues() && user.isPresent()) {
            Optional<EntityChild> result = serviceChild.saveChildByPostDTO(child, user.get());
            return result.map(response -> ResponseEntity.ok().body(child))
                    .orElse(ResponseEntity.badRequest().build());
        }
        return ResponseEntity.badRequest().build();
    }


}
