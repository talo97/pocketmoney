package com.ioproject.pocketmoney.dao;

import com.ioproject.pocketmoney.entities.EntityAdministrationUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoAdministrationUnit extends JpaRepository<EntityAdministrationUnit, Long> {
}
