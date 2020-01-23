package com.ioproject.pocketmoney.entities;

import com.ioproject.pocketmoney.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "administration_unit")
@Getter
@Setter
public class EntityAdministrationUnit extends CommonEntity {

    @Column(name = "country")
    private String country;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

}
