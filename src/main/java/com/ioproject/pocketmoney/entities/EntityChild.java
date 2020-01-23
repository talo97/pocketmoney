package com.ioproject.pocketmoney.entities;

import com.ioproject.pocketmoney.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "child")
@Getter
@Setter
public class EntityChild extends CommonEntity {

    @Column(name = "pocket_money_value")
    private Float pocketMoney;

    @Column(name = "description")
    private String description;

    @Column(name = "sex")
    private String sex;

    @Column(name = "is_living_with_parents")
    private boolean isLivingWithParents;

    @ManyToOne(fetch = FetchType.EAGER) //maybe change later for LAZY (dunno tho)
    @JoinColumn(name = "administration_unit") //nullable = true
    private EntityAdministrationUnit administrationUnit;

    @ManyToOne(fetch = FetchType.EAGER) //maybe change later for LAZY (dunno tho)
    @JoinColumn(name = "education") //nullable = true
    private EntityEducation education;

}
