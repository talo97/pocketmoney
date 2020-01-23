package com.ioproject.pocketmoney.dao;

import com.ioproject.pocketmoney.entities.EntityGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

@Repository
public interface DaoGroup extends JpaRepository<EntityGroup, Long> {
    Optional<EntityGroup> findByGroupName(String groupName);
}
