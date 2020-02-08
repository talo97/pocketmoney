package com.ioproject.pocketmoney.service;

import com.ioproject.pocketmoney.common.CommonService;
import com.ioproject.pocketmoney.entities.EntityAdministrationUnit;
import com.ioproject.pocketmoney.entities.EntityChild;
import com.ioproject.pocketmoney.entities.EntityEducation;
import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.entitiesDTO.ChildPostDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ServiceChild extends CommonService<EntityChild, Long> {
    Optional<EntityChild> saveChildByPostDTO(ChildPostDTO dto, EntityUser currentUser);
    List<EntityChild> getAllByUser(EntityUser user);
    EntityChild updateChildByDTO(ChildPostDTO childPostDTO, EntityChild currentChild);

    Float calculateAverageMoney();
    Float calculateAverageMoney(EntityAdministrationUnit entityAdministrationUnit);
    Float calculateAverageMoney(EntityAdministrationUnit entityAdministrationUnit, EntityEducation entityEducation);
    Float calculateAverageMoney(EntityEducation entityEducation);

    List<EntityChild> getAllByAdministrationUnitAndEducation(EntityAdministrationUnit entityAdministrationUnit, EntityEducation entityEducation);

    void deleteChildrenFromUser(EntityUser user);
}
