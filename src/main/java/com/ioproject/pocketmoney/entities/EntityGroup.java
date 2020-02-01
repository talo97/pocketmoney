package com.ioproject.pocketmoney.entities;

import com.ioproject.pocketmoney.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_group")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityGroup extends CommonEntity {

    @Column(name = "group_name", unique = true)
    private String groupName;

}
