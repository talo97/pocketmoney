package com.ioproject.pocketmoney.entitiesDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NameFloatForTableDTO {
    private String name;
    private Float value = 0f;
}
