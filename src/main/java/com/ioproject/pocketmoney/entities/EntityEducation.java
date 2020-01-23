package com.ioproject.pocketmoney.entities;

import com.ioproject.pocketmoney.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "education")
@Getter
@Setter
public class EntityEducation extends CommonEntity {

    @Column(name = "year")
    private Integer educationYear;

    @Column(name = "level")
    private String educationLevel;
}
