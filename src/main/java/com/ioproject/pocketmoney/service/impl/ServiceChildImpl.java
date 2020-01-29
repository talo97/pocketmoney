package com.ioproject.pocketmoney.service.impl;

import com.ioproject.pocketmoney.common.CommonServiceImpl;
import com.ioproject.pocketmoney.dao.DaoChild;
import com.ioproject.pocketmoney.entities.EntityAdministrationUnit;
import com.ioproject.pocketmoney.entities.EntityChild;
import com.ioproject.pocketmoney.entities.EntityEducation;
import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.entitiesDTO.EntityChildPostDTO;
import com.ioproject.pocketmoney.service.ServiceAdministrationUnit;
import com.ioproject.pocketmoney.service.ServiceChild;
import com.ioproject.pocketmoney.service.ServiceEducation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceChildImpl extends CommonServiceImpl<EntityChild, DaoChild, Long> implements ServiceChild {

    @Autowired
    private ServiceAdministrationUnit serviceAdministrationUnit;

    @Autowired
    private ServiceEducation serviceEducation;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ServiceChildImpl(DaoChild repository) {
        super(repository);
    }

    @Override
    public Optional<EntityChild> saveChildByPostDTO(EntityChildPostDTO dto, EntityUser currentUser) {
        //find adm unit by name
        Optional<EntityAdministrationUnit> administrationUnit = serviceAdministrationUnit.getByCityAndProvince(dto.getCity(), dto.getProvince());
        //find edu lvl by name
        Optional<EntityEducation> education = serviceEducation.getByEducationLevel(dto.getEducationLevel());
        //if both of them exist then create entity with given user
        if(administrationUnit.isPresent() &&education.isPresent()){
            EntityChild toSave = modelMapper.map(dto, EntityChild.class);
            toSave.setAdministrationUnit(administrationUnit.get());
            toSave.setEducation(education.get());
            toSave.setUser(currentUser);
            return Optional.of(repository.save(toSave));
        }
        return Optional.empty();
        //save and return
    }
}
