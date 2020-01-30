package com.ioproject.pocketmoney.service;

import com.ioproject.pocketmoney.common.CommonService;
import com.ioproject.pocketmoney.entities.EntityAdministrationUnit;
import com.ioproject.pocketmoney.entities.EntityUser;

import java.util.Optional;

public interface ServiceAdministrationUnit extends CommonService<EntityAdministrationUnit, Long> {
    Optional<EntityAdministrationUnit> getByName(String name);
}
