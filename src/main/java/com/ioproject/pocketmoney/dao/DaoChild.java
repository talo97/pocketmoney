package com.ioproject.pocketmoney.dao;

import com.ioproject.pocketmoney.entities.EntityChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoChild extends JpaRepository<EntityChild, Long> {
}
