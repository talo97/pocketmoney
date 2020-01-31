package com.ioproject.pocketmoney.entitiesDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserEditDTO {

    private String password;

    private String name;

    private String surname;

    /**
     * @return true if object has not all values besides the ID
     */
    public boolean containsEmptyValue(){
        return  password == null || name == null || surname == null;
    }

}
