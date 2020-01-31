package com.ioproject.pocketmoney.entitiesDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdmUnitAvgMoneyDTO {
    private String administrationUnit;
    private Float averageMoney = 0f;
}
