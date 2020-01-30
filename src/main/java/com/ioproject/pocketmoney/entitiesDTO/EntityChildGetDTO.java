package com.ioproject.pocketmoney.entitiesDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EntityChildGetDTO {
    private Long id;
    private Long userId;
    private String user;
    private Float pocketMoney;
    private String description = "";
    private String sex;
    private Boolean isLivingWithParents;
    private String administrationUnit;
    private String educationLevel;

}
