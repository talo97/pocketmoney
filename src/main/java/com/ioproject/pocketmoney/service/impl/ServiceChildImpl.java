package com.ioproject.pocketmoney.service.impl;

import com.ioproject.pocketmoney.common.CommonServiceImpl;
import com.ioproject.pocketmoney.dao.DaoChild;
import com.ioproject.pocketmoney.entities.EntityAdministrationUnit;
import com.ioproject.pocketmoney.entities.EntityChild;
import com.ioproject.pocketmoney.entities.EntityEducation;
import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.entitiesDTO.ChildPostDTO;
import com.ioproject.pocketmoney.service.ServiceAdministrationUnit;
import com.ioproject.pocketmoney.service.ServiceChild;
import com.ioproject.pocketmoney.service.ServiceEducation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceChildImpl extends CommonServiceImpl<EntityChild, DaoChild, Long> implements ServiceChild {

    private final ServiceAdministrationUnit serviceAdministrationUnit;

    private final ServiceEducation serviceEducation;

    private final ModelMapper modelMapper;

    @Autowired
    public ServiceChildImpl(DaoChild repository, ServiceAdministrationUnit serviceAdministrationUnit, ServiceEducation serviceEducation, ModelMapper modelMapper) {
        super(repository);
        this.serviceAdministrationUnit = serviceAdministrationUnit;
        this.serviceEducation = serviceEducation;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<EntityChild> saveChildByPostDTO(ChildPostDTO dto, EntityUser currentUser) {
        //find adm unit by name
        Optional<EntityAdministrationUnit> administrationUnit = serviceAdministrationUnit.getByName(dto.getAdministrationUnit());
        //find edu lvl by name
        Optional<EntityEducation> education = serviceEducation.getByEducationLevel(dto.getEducationLevel());
        //if both of them exist then create entity with given user
        if (administrationUnit.isPresent() && education.isPresent()) {
            EntityChild toSave = modelMapper.map(dto, EntityChild.class);
            toSave.setAdministrationUnit(administrationUnit.get());
            toSave.setEducation(education.get());
            toSave.setUser(currentUser);
            return Optional.of(repository.save(toSave));
        }
        return Optional.empty();
        //save and return
    }

    @Override
    public List<EntityChild> getAllByUser(EntityUser user) {
        return repository.getAllByUser(user);
    }

    @Override
    public EntityChild updateChildByDTO(ChildPostDTO childPostDTO, EntityChild currentChild) {
        if (childPostDTO.getAdministrationUnit() != null) {
            Optional<EntityAdministrationUnit> administrationUnit = serviceAdministrationUnit.getByName(childPostDTO.getAdministrationUnit());
            if (administrationUnit.isPresent()) {
                currentChild.setAdministrationUnit(administrationUnit.get());
            }
        }
        if (childPostDTO.getEducationLevel() != null) {
            Optional<EntityEducation> entityEducation = serviceEducation.getByEducationLevel(childPostDTO.getEducationLevel());
            if (entityEducation.isPresent()) {
                currentChild.setEducation(entityEducation.get());
            }
        }
        if (childPostDTO.getIsLivingWithParents() != null) {
            currentChild.setLivingWithParents(childPostDTO.getIsLivingWithParents());
        }
        if (childPostDTO.getDescription() != null) {
            currentChild.setDescription(childPostDTO.getDescription());
        }
        if (childPostDTO.getSex() != null) {
            currentChild.setSex(childPostDTO.getSex());
        }
        if (childPostDTO.getPocketMoney() != null) {
            childPostDTO.setPocketMoney(childPostDTO.getPocketMoney());
        }
        return repository.saveAndFlush(currentChild);
    }

    @Override
    public Float calculateAverageMoneyForAdministrationUnit(EntityAdministrationUnit entityAdministrationUnit) {
        int counter = 0;
        Float averageValue = 0f;
        List<EntityChild> children = repository.findAllByAdministrationUnit(entityAdministrationUnit);
        // find if it is province and if it is then add all children from them
        List<EntityAdministrationUnit> cities;
        if (entityAdministrationUnit.getProvince() == null) {
            cities = serviceAdministrationUnit.getAllByProvince(entityAdministrationUnit);
            cities.forEach(entity -> {
                children.addAll(repository.findAllByAdministrationUnit(entity));
            });
        }
        for (EntityChild child : children) {
            counter++;
            averageValue += child.getPocketMoney();
        }
        return averageValue / counter;
    }

    @Override
    public Float calculateAverageMoneyForAdministrationUnitAndEducation(EntityAdministrationUnit entityAdministrationUnit, EntityEducation entityEducation) {
        float averageValue = 0f;
        List<EntityChild> children = repository.findAllByAdministrationUnitAndEducation(entityAdministrationUnit, entityEducation);
        for (EntityChild child : children) {
            averageValue += child.getPocketMoney();
        }
        return averageValue;
    }
}
