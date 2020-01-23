package com.ioproject.pocketmoney.dao;

import com.ioproject.pocketmoney.entities.EntityEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaoEducation extends JpaRepository<EntityEducation, Long>{
    List<EntityEducation> findByEducationLevel(String username);
}
