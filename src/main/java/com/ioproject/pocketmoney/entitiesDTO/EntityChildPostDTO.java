package com.ioproject.pocketmoney.entitiesDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityChildPostDTO {
    private Float pocketMoney;
    private String description = "";
    private String sex;
    private Boolean isLivingWithParents;
    private String city;
    private String province;
    private String educationLevel;

    /**
     * @return true if object has not all required values
     */
    public boolean doesntContainAllRequiredValues(){
        return  pocketMoney == null || sex == null || isLivingWithParents == null ||  city == null || province == null ||  educationLevel == null;
    }
}
