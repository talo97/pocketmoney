package com.ioproject.pocketmoney.service;

import com.ioproject.pocketmoney.common.CommonService;
import com.ioproject.pocketmoney.entities.EntityChild;
import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.entitiesDTO.EntityChildPostDTO;

import java.util.List;
import java.util.Optional;

public interface ServiceChild extends CommonService<EntityChild, Long> {
    Optional<EntityChild> saveChildByPostDTO(EntityChildPostDTO dto, EntityUser currentUser);
    List<EntityChild> getAllByUser(EntityUser user);
}
