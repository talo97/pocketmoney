package com.ioproject.pocketmoney.service;

import com.ioproject.pocketmoney.common.CommonService;
import com.ioproject.pocketmoney.entities.EntityGroup;

import java.util.Optional;

public interface ServiceGroup extends CommonService<EntityGroup, Long> {
    void deleteAllGroups();
    Optional<EntityGroup> getByGroupName(String groupName);
}
