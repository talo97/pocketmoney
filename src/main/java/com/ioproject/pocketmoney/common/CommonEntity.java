package com.ioproject.pocketmoney.common;

import lombok.Getter;

import javax.persistence.*;

@MappedSuperclass
@Getter
public class CommonEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;


}
