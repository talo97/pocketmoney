package com.ioproject.pocketmoney.entities;

import com.ioproject.pocketmoney.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
public class EntityUser extends CommonEntity{

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @ManyToOne(fetch = FetchType.EAGER) //maybe change later for LAZY (dunno tho)
    @JoinColumn(name = "user_group") //nullable = true
    private EntityGroup userGroup;

}
