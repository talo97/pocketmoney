package com.ioproject.pocketmoney.entitiesDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * acts as POST and PUT DTO (add and edit)
 */
@Getter
@Setter
public class ChildPostDTO {
    private Float pocketMoney;
    private String description = "";
    private String sex;
    private Boolean isLivingWithParents;
    private String administrationUnit;
    private String educationLevel;

    /**
     * @return true if object has not all required values (used for adding new child)
     */
    public boolean doesntContainAllRequiredValues() {
        return pocketMoney == null || sex == null || isLivingWithParents == null || administrationUnit == null || educationLevel == null || notCorrectPocketMoneyValue();
    }

    public boolean notCorrectPocketMoneyValue(){
        return pocketMoney < 0 || pocketMoney > 5000;
    }
}
