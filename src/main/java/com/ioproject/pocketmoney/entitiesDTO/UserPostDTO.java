package com.ioproject.pocketmoney.entitiesDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPostDTO {

    private String username;

    private String password;

    private String email;

    private String name;

    private String surname;

    /**
     * @return true if object has not all values besides the ID
     */
    public boolean containsEmptyValue(){
        return username == null || password == null || email == null || name == null || surname == null;
    }
}
