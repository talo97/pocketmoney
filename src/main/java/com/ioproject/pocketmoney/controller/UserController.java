package com.ioproject.pocketmoney.controller;

import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.entitiesDTO.EntityUserGetDTO;
import com.ioproject.pocketmoney.entitiesDTO.EntityUserEditDTO;
import com.ioproject.pocketmoney.entitiesDTO.EntityUserPostDTO;
import com.ioproject.pocketmoney.service.ServiceUser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private ServiceUser serviceUser;

    @Autowired
    private ModelMapper modelMapper;

    private Optional<EntityUser> getCurrentUserFromToken() {
        return serviceUser.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    //TODO:: maybe convert return value to EntityUserGetDTO so it will be more consistent
    // but I guess this functionality will not be even used so I don't bother XD
    @GetMapping("/users")
    public Collection<EntityUser> users() {
        log.info("Request to get all users");
        return serviceUser.getAll();
    }

    @GetMapping("/currentUser")
    public ResponseEntity<EntityUserGetDTO> getCurrentUser() {
        log.info("Request to get current user");
        Optional<EntityUser> currentUser = getCurrentUserFromToken();
        Optional<EntityUserGetDTO> currentUserDTO = Optional.empty();
        if (currentUser.isPresent()) {
            currentUserDTO = Optional.of(modelMapper.map(currentUser.get(), EntityUserGetDTO.class));
            currentUserDTO.get().setUserGroup(currentUser.get().getUserGroup().getGroupName());
        }
        return currentUserDTO.map(response -> ResponseEntity.ok().body(response))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/addUser")
    public ResponseEntity<EntityUserPostDTO> createUser(@Valid @RequestBody EntityUserPostDTO user) throws URISyntaxException {
        log.info("Request to create user: {}", user);
        if (user.containsEmptyValue()) {
            return ResponseEntity.badRequest().build();
        }
        Optional<EntityUser> result = serviceUser.saveUserByDTO(user);
        return result.map(response -> ResponseEntity.ok().body(modelMapper.map(response, EntityUserPostDTO.class)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        log.info("Request to delete user: {}", id);
        try {
            serviceUser.delete(id);
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/editCurrentUser")
    public ResponseEntity<EntityUserEditDTO> updateCurrentUser(@Valid @RequestBody EntityUserEditDTO userEdit) {
        log.info("Request to update current user: {}", userEdit);
        if (!userEdit.containsEmptyValue()) {
            Optional<EntityUser> currentUser = getCurrentUserFromToken();
            if (currentUser.isPresent()) {
                currentUser.get().setPassword(userEdit.getPassword());
                currentUser.get().setName(userEdit.getName());
                currentUser.get().setSurname(userEdit.getSurname());
                //save to DB
                serviceUser.update(currentUser.get());
                return ResponseEntity.ok().body(userEdit);
            }
        }
        return ResponseEntity.badRequest().build();
    }
//    @DeleteMapping("/group/deleteAll")
//    public ResponseEntity<?> deleteAll() {
//        log.info("Request to delete all groups");
//        serviceUser.deleteAll();
//        return ResponseEntity.ok().build();
//    }

    //    @PutMapping("/user/{id}")
//    ResponseEntity<EntityUser> updateGroup(@Valid @RequestBody EntityUser group, @PathVariable Long id) {
//        log.info("Request to update group: {}", group);
//        Optional<EntityUser> fromId = serviceUser.get(id);
//        EntityUser result;
//        if (fromId.isPresent()) {
//            fromId.get().set(group.getGroupName());
//            serviceUser.update(fromId.get());
//            return ResponseEntity.ok().body(fromId.get());
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

}
