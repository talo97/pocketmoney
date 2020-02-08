package com.ioproject.pocketmoney.dao;

import com.ioproject.pocketmoney.entities.EntityGroup;
import com.ioproject.pocketmoney.entities.EntityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DaoUser extends JpaRepository<EntityUser, Long>{
    Optional<EntityUser> findByUsername(String name);
    Optional<EntityUser> findByEmail(String email);
    List<EntityUser> findAllByUserGroup(EntityGroup group);
}
