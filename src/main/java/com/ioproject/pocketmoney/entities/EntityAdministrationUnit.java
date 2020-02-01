package com.ioproject.pocketmoney.entities;

import com.ioproject.pocketmoney.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "administration_unit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityAdministrationUnit extends CommonEntity {

    //unique name for the city/province
    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER) //maybe change later for LAZY (dunno tho)
    @JoinColumn(name = "province") //if record is big city then here should be  the province// if record is already province then null
    private EntityAdministrationUnit province;


}
