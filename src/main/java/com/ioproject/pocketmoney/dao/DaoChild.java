package com.ioproject.pocketmoney.dao;

import com.ioproject.pocketmoney.entities.EntityAdministrationUnit;
import com.ioproject.pocketmoney.entities.EntityChild;
import com.ioproject.pocketmoney.entities.EntityEducation;
import com.ioproject.pocketmoney.entities.EntityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaoChild extends JpaRepository<EntityChild, Long> {
    List<EntityChild> findAllByUser(EntityUser user);
    List<EntityChild> findAllByAdministrationUnit(EntityAdministrationUnit entityAdministrationUnit);
    List<EntityChild> findAllByAdministrationUnitAndEducation(EntityAdministrationUnit entityAdministrationUnit, EntityEducation entityEducation);
    List<EntityChild> findAllByEducation(EntityEducation entityEducation);
}
