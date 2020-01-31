package com.ioproject.pocketmoney.controller;

import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.entitiesDTO.UserGetDTO;
import com.ioproject.pocketmoney.entitiesDTO.UserEditDTO;
import com.ioproject.pocketmoney.entitiesDTO.UserPostDTO;
import com.ioproject.pocketmoney.entitiesDTO.NameDTO;
import com.ioproject.pocketmoney.service.ServiceUser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(GroupController.class);

    private final ServiceUser serviceUser;

    private final ModelMapper modelMapper;

    public UserController(ServiceUser serviceUser, ModelMapper modelMapper) {
        this.serviceUser = serviceUser;
        this.modelMapper = modelMapper;
    }

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

    /**
     * @return currently logged in user
     */
    @GetMapping("/currentUser")
    public ResponseEntity<UserGetDTO> getCurrentUser() {
        log.info("Request to get current user");
        Optional<EntityUser> currentUser = getCurrentUserFromToken();
        Optional<UserGetDTO> currentUserDTO = Optional.empty();
        if (currentUser.isPresent()) {
            currentUserDTO = Optional.of(modelMapper.map(currentUser.get(), UserGetDTO.class));
            currentUserDTO.get().setUserGroup(currentUser.get().getUserGroup().getGroupName());
        }
        return currentUserDTO.map(response -> ResponseEntity.ok().body(response))
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * add user to database with default group privileges
     *
     * @param user DTO
     * @return added user
     */
    @PostMapping("/addUser")
    public ResponseEntity<UserPostDTO> createUser(@Valid @RequestBody UserPostDTO user) {
        log.info("Request to create user: {}", user);
        if (user.containsEmptyValue()) {
            return ResponseEntity.badRequest().build();
        }
        Optional<EntityUser> result;
        result = serviceUser.saveUserByDTO(user);

        return result.map(response -> ResponseEntity.ok().body(modelMapper.map(response, UserPostDTO.class)))
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

    @PostMapping(value = "/validateUsername")
    public ResponseEntity<Boolean> checkIfUsernameIsAvailable(@Valid @RequestBody NameDTO name) {
        if (serviceUser.getByUsername(name.getName()).isPresent()) {
            return ResponseEntity.ok().body(false);
        } else {
            return ResponseEntity.ok().body(true);
        }
    }

    @PostMapping("/validateEmail")
    public ResponseEntity<Boolean> checkIfEmailIsAvailable(@Valid @RequestBody NameDTO name) {
        if (serviceUser.getByEmail(name.getName()).isPresent()) {
            return ResponseEntity.ok().body(false);
        } else {
            return ResponseEntity.ok().body(true);
        }
    }

    @PutMapping("/editCurrentUser")
    public ResponseEntity<UserGetDTO> updateCurrentUser(@Valid @RequestBody UserEditDTO userEdit) {
        log.info("Request to update current user: {}", userEdit);
        Optional<EntityUser> currentUser = getCurrentUserFromToken();
        if (currentUser.isPresent()) {
            if (userEdit.getPassword() != null) {
                currentUser.get().setPassword(userEdit.getPassword());
            }
            if (userEdit.getName() != null) {
                currentUser.get().setName(userEdit.getName());
            }
            if (userEdit.getSurname() != null) {
                currentUser.get().setSurname(userEdit.getSurname());
            }
            //save to DB
            serviceUser.update(currentUser.get());

            Optional<UserGetDTO> currentUserDTO;
            currentUserDTO = Optional.of(modelMapper.map(currentUser.get(), UserGetDTO.class));
            currentUserDTO.get().setUserGroup(currentUser.get().getUserGroup().getGroupName());
            return currentUserDTO.map(response -> ResponseEntity.ok().body(response))
                    .orElse(ResponseEntity.badRequest().build());
        }
        return ResponseEntity.badRequest().build();
    }
//    @DeleteMapping("/group/deleteAll")
//    public ResponseEntity<?> deleteAll() {
//        log.info("Request to delete all groups");
//        serviceUser.deleteAll();
//        return ResponseEntity.ok().build();
//    }


}
