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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
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
        return repository.findAllByUser(user);
    }

    @Override
    public EntityChild updateChildByDTO(ChildPostDTO childPostDTO, EntityChild currentChild) {
        if (childPostDTO.getAdministrationUnit() != null) {
            Optional<EntityAdministrationUnit> administrationUnit = serviceAdministrationUnit.getByName(childPostDTO.getAdministrationUnit());
            administrationUnit.ifPresent(currentChild::setAdministrationUnit);
        }
        if (childPostDTO.getEducationLevel() != null) {
            Optional<EntityEducation> entityEducation = serviceEducation.getByEducationLevel(childPostDTO.getEducationLevel());
            entityEducation.ifPresent(currentChild::setEducation);
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
            currentChild.setPocketMoney(childPostDTO.getPocketMoney());
        }
        return repository.saveAndFlush(currentChild);
    }

    @Override
    public Float calculateAverageMoney() {
        List<EntityChild> children = repository.findAll();
        int counter = 0;
        Float averageValue = 0f;
        return getAverageMoneyFromChildList(averageValue, counter, children);
    }

    @Override
    public Float calculateAverageMoney(EntityAdministrationUnit entityAdministrationUnit) {
        int counter = 0;
        Float averageValue = 0f;
        List<EntityChild> children = repository.findAllByAdministrationUnit(entityAdministrationUnit);
        // find if it is province and if it is then add all children from them
        if (entityAdministrationUnit.getProvince() == null) {
            List<EntityAdministrationUnit> cities = serviceAdministrationUnit.getAllByProvince(entityAdministrationUnit);
            cities.forEach(entity -> children.addAll(repository.findAllByAdministrationUnit(entity)));
        }
        return getAverageMoneyFromChildList(averageValue, counter, children);
    }

    @Override
    public Float calculateAverageMoney(EntityAdministrationUnit entityAdministrationUnit, EntityEducation entityEducation) {
        float averageValue = 0f;
        int counter = 0;
        List<EntityChild> children = repository.findAllByAdministrationUnitAndEducation(entityAdministrationUnit, entityEducation);
        if (entityAdministrationUnit.getProvince() == null) {
            List<EntityAdministrationUnit> cities = serviceAdministrationUnit.getAllByProvince(entityAdministrationUnit);
            cities.forEach(entity -> children.addAll(repository.findAllByAdministrationUnitAndEducation(entity, entityEducation)));
        }
        return getAverageMoneyFromChildList(averageValue, counter, children);
    }

    @Override
    public Float calculateAverageMoney(EntityEducation entityEducation) {
        float averageValue = 0f;
        int counter = 0;
        List<EntityChild> children = repository.findAllByEducation(entityEducation);
        return getAverageMoneyFromChildList(averageValue, counter, children);
    }

    private Float getAverageMoneyFromChildList(float averageValue, int counter, List<EntityChild> children) {
        for (EntityChild child : children) {
            counter++;
            averageValue += child.getPocketMoney();
        }
        if (counter == 0) return averageValue;
        BigDecimal roundResult = new BigDecimal( averageValue / counter).setScale(2, RoundingMode.CEILING);
        return roundResult.floatValue();
    }

    @Override
    public List<EntityChild> getAllByAdministrationUnitAndEducation(EntityAdministrationUnit entityAdministrationUnit, EntityEducation entityEducation) {
        List<EntityChild> children = repository.findAllByAdministrationUnitAndEducation(entityAdministrationUnit, entityEducation);
        if (entityAdministrationUnit.getProvince() == null) {
            List<EntityAdministrationUnit> cities = serviceAdministrationUnit.getAllByProvince(entityAdministrationUnit);
            cities.forEach(entity -> children.addAll(repository.findAllByAdministrationUnitAndEducation(entity, entityEducation)));
        }
        return children;
    }

    @Override
    public void deleteChildrenFromUser(EntityUser user) {
        repository.findAllByUser(user).forEach(entityChild ->
                repository.delete(entityChild));
    }
}
