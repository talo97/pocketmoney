package com.ioproject.pocketmoney.service.impl;

import com.ioproject.pocketmoney.common.CommonServiceImpl;
import com.ioproject.pocketmoney.dao.DaoUser;
import com.ioproject.pocketmoney.entities.EntityGroup;
import com.ioproject.pocketmoney.entities.EntityUser;
import com.ioproject.pocketmoney.entitiesDTO.UserPostDTO;
import com.ioproject.pocketmoney.service.ServiceGroup;
import com.ioproject.pocketmoney.service.ServiceUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceUserImpl extends CommonServiceImpl<EntityUser, DaoUser, Long> implements ServiceUser {

    @Autowired
    public ServiceUserImpl(DaoUser repository, ServiceGroup serviceGroup, ModelMapper modelMapper) {
        super(repository);
        this.serviceGroup = serviceGroup;
        this.modelMapper = modelMapper;
    }

    private final ServiceGroup serviceGroup;
    private final ModelMapper modelMapper;

    @Override
    public Optional<EntityUser> getByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    @Override
    public Optional<EntityUser> getByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    @Override
    public Optional<EntityUser> saveUserByDTO(UserPostDTO entityUserGetDTO) {
        //TODO:: maybe do it a bit better XD like take the value from some constant class or app proporties??, dunno for now imma leave it like that
        Optional<EntityGroup> group = serviceGroup.getByGroupName("DEFAULT");
        Optional<EntityUser> user = Optional.empty();
        if(group.isPresent()){
            user = Optional.of(modelMapper.map(entityUserGetDTO, EntityUser.class));
            user.get().setUserGroup(group.get());
            this.save(user.get());
        }
        return user;
    }
}
