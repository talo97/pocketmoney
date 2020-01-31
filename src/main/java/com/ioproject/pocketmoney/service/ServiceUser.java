package com.ioproject.pocketmoney.service;

import com.ioproject.pocketmoney.common.CommonService;
import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.entitiesDTO.EntityUserPostDTO;

import java.util.Optional;

public interface ServiceUser extends CommonService<EntityUser, Long> {
    Optional<EntityUser> getByUsername(String name);
    Optional<EntityUser> getByEmail(String email);
    Optional<EntityUser> saveUserByDTO(EntityUserPostDTO entityUserGetDTO);
}
