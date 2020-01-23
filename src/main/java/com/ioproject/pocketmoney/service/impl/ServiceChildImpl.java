package com.ioproject.pocketmoney.service.impl;

import com.ioproject.pocketmoney.common.CommonServiceImpl;
import com.ioproject.pocketmoney.dao.DaoChild;
import com.ioproject.pocketmoney.entities.EntityChild;
import com.ioproject.pocketmoney.service.ServiceChild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceChildImpl extends CommonServiceImpl<EntityChild, DaoChild, Long> implements ServiceChild {

    @Autowired
    public ServiceChildImpl(DaoChild repository) {
        super(repository);
    }
}
