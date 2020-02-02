package com.ioproject.pocketmoney.controller;

import com.ioproject.pocketmoney.entities.EntityGroup;
import com.ioproject.pocketmoney.service.ServiceGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
//a lot of not needed functionality (was used only for testing purposes)
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class GroupController {
    private final Logger log = LoggerFactory.getLogger(GroupController.class);

    private final ServiceGroup serviceGroup;

    public GroupController(ServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
    }


    @GetMapping("/groups")
    public Collection<EntityGroup> groups() {
        return serviceGroup.getAll();
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<?> getGroup(@PathVariable Long id) {
        Optional<EntityGroup> group = serviceGroup.get(id);
        return group.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/group")
    ResponseEntity<EntityGroup> createGroup(@Valid @RequestBody EntityGroup group) throws URISyntaxException {
        log.info("Request to create group: {}", group);
        EntityGroup result = serviceGroup.save(group);
        return ResponseEntity.created(new URI("/api/group/" + result.getId()))
                .body(result);
    }

    @PutMapping("/group/{id}")
    ResponseEntity<EntityGroup> updateGroup(@Valid @RequestBody EntityGroup group, @PathVariable Long id) {
        log.info("Request to update group: {}", group);
        Optional<EntityGroup> fromId = serviceGroup.get(id);
        EntityGroup result;
        if (fromId.isPresent()) {
            fromId.get().setGroupName(group.getGroupName());
            serviceGroup.update(fromId.get());
            return ResponseEntity.ok().body(fromId.get());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        log.info("Request to delete group: {}", id);
        serviceGroup.delete(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/group/deleteAll")
    public ResponseEntity<?> deleteAll() {
        log.info("Request to delete all groups");
        serviceGroup.deleteAllGroups();
        return ResponseEntity.ok().build();
    }
}
