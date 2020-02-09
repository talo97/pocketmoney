package com.ioproject.pocketmoney.entitiesDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserGetDTO {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String name;

    private String surname;

    private String userGroup;

}
