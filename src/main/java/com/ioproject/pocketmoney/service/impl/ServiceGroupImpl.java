package com.ioproject.pocketmoney.service.impl;

import com.ioproject.pocketmoney.common.CommonServiceImpl;
import com.ioproject.pocketmoney.dao.DaoGroup;
import com.ioproject.pocketmoney.entities.EntityGroup;
import com.ioproject.pocketmoney.service.ServiceGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ServiceGroupImpl extends CommonServiceImpl<EntityGroup, DaoGroup, Long> implements ServiceGroup {

    @Autowired
    public ServiceGroupImpl(DaoGroup repository) {
        super(repository);
    }

    @Override
    public void deleteAllGroups() {
        repository.deleteAll();
    }

    @Override
    public Optional<EntityGroup> getByGroupName(String groupName) {
        return repository.findByGroupName(groupName);
    }
}
