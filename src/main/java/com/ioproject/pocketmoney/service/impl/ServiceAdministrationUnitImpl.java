package com.ioproject.pocketmoney.service.impl;

import com.ioproject.pocketmoney.common.CommonServiceImpl;
import com.ioproject.pocketmoney.dao.DaoAdministrationUnit;
import com.ioproject.pocketmoney.entities.EntityAdministrationUnit;
import com.ioproject.pocketmoney.service.ServiceAdministrationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceAdministrationUnitImpl extends CommonServiceImpl<EntityAdministrationUnit, DaoAdministrationUnit, Long> implements ServiceAdministrationUnit {

    @Autowired
    public ServiceAdministrationUnitImpl(DaoAdministrationUnit repository) {
        super(repository);
    }

    @Override
    public Optional<EntityAdministrationUnit> getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<EntityAdministrationUnit> getAllByProvince(EntityAdministrationUnit province) {
        return repository.findAllByProvince(province);
    }
}
