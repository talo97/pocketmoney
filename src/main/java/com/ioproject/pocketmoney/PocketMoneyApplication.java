package com.ioproject.pocketmoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PocketMoneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocketMoneyApplication.class, args);
    }

    //TODO::
    //WEB security (w miare zrobione, testy itp.)
    //ogarnac REST z foreign key - rozwiazana za pomoca Data Access Object (DTO)
    //kontrolki do child wszystkie do zrobienia
    //przygotowac gotowca do mysql (jakis skrypcik zeby zapelnic adm_unit/school/groups)
    //w sumie api bedzie zarzadzac tylko user i child wiec spoko xd
}
