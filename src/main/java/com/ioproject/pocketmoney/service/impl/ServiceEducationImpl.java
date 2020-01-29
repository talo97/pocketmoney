package com.ioproject.pocketmoney.service.impl;

import com.ioproject.pocketmoney.common.CommonServiceImpl;
import com.ioproject.pocketmoney.dao.DaoEducation;
import com.ioproject.pocketmoney.entities.EntityEducation;
import com.ioproject.pocketmoney.service.ServiceEducation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceEducationImpl extends CommonServiceImpl<EntityEducation, DaoEducation, Long> implements ServiceEducation {

    @Autowired
    public ServiceEducationImpl(DaoEducation repository) {
        super(repository);
    }

    @Override
    public Optional<EntityEducation> getByEducationLevel(String educationLevel) {
        return repository.findFirstByEducationLevel(educationLevel);
    }
}
