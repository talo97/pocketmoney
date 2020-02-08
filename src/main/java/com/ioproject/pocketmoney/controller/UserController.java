package com.ioproject.pocketmoney.controller;

import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.entitiesDTO.UserGetDTO;
import com.ioproject.pocketmoney.entitiesDTO.UserEditDTO;
import com.ioproject.pocketmoney.entitiesDTO.UserPostDTO;
import com.ioproject.pocketmoney.entitiesDTO.NameDTO;
import com.ioproject.pocketmoney.service.ServiceChild;
import com.ioproject.pocketmoney.service.ServiceGroup;
import com.ioproject.pocketmoney.service.ServiceUser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(GroupController.class);

    private final ServiceUser serviceUser;

    private final ServiceChild serviceChild;

    private final ServiceGroup serviceGroup;

    private final ModelMapper modelMapper;

    public UserController(ServiceUser serviceUser, ServiceChild serviceChild,ServiceGroup serviceGroup, ModelMapper modelMapper) {
        this.serviceUser = serviceUser;
        this.serviceChild = serviceChild;
        this.serviceGroup = serviceGroup;
        this.modelMapper = modelMapper;
    }

    private Optional<EntityUser> getCurrentUserFromToken() {
        return serviceUser.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private List<UserGetDTO> mapListUserToGetDTO(List<EntityUser> entityUsers){
        List<UserGetDTO> toReturn = new ArrayList<>();
        entityUsers.forEach(user -> {
            UserGetDTO tmp;
            tmp = modelMapper.map(user, UserGetDTO.class);
            tmp.setUserGroup(user.getUserGroup().getGroupName());
            toReturn.add(tmp);
        });
        return toReturn;
    }

    @GetMapping("/getDefaultUsers")
    public Collection<UserGetDTO> users() {
        log.info("Request to get all default users");
        return mapListUserToGetDTO(serviceUser.getAllByUserGroup(serviceGroup.getByGroupName("DEFAULT").get()));
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

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.info("Request to delete user: {}", id);
        Optional<EntityUser> user = serviceUser.get(id);
        user.ifPresent(e -> {
            this.serviceChild.deleteChildrenFromUser(user.get());
            serviceUser.delete(user.get());
        });
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
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
        if (user.containsEmptyValue() || !checkIfEmailIsAvailable(user.getEmail()) || !checkIfUsernameIsAvailable(user.getUsername())
                || !validateEmail(user.getEmail()) || !validateUserName(user.getUsername()) || !validatePassword(user.getPassword())) {
            return ResponseEntity.badRequest().build();
        }
        Optional<EntityUser> result;
        result = serviceUser.saveUserByDTO(user);
        return result.map(response -> ResponseEntity.ok().body(modelMapper.map(response, UserPostDTO.class)))
                .orElse(ResponseEntity.badRequest().build());
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
            if (userEdit.getPassword() != null && validatePassword(userEdit.getPassword())) {
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

    private boolean checkIfUsernameIsAvailable(String username) {
        return !serviceUser.getByUsername(username).isPresent();
    }

    private boolean checkIfEmailIsAvailable(String email) {
        return !serviceUser.getByEmail(email).isPresent();
    }

    private final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^.{5,}$", Pattern.CASE_INSENSITIVE);

    private final Pattern VALID_USERNAME_REGEX = Pattern.compile("^[a-z0-9_-]{3,25}$", Pattern.CASE_INSENSITIVE);

    private boolean validateUserName(String username) {
        Matcher matcher = VALID_USERNAME_REGEX.matcher(username);
        return matcher.matches();
    }

    private boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.matches();
    }

}
