package com.ioproject.pocketmoney.service;

import com.ioproject.pocketmoney.common.CommonService;
import com.ioproject.pocketmoney.entities.EntityEducation;
import com.ioproject.pocketmoney.entities.EntityGroup;
import com.ioproject.pocketmoney.entities.EntityUser;

import java.util.Optional;

public interface ServiceEducation extends CommonService<EntityEducation, Long> {
    Optional<EntityEducation> getByEducationLevel(String educationLevel);
}
