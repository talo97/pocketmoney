package com.ioproject.pocketmoney.test;

import com.ioproject.pocketmoney.entities.EntityGroup;
import com.ioproject.pocketmoney.service.ServiceGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @RequestMapping("/")
    public String home(){
        return "hello wolrd!";
    }
    @RequestMapping("/user")
    public String user(){
        return "user only!";
    }
    @RequestMapping("/admin")
    public String admin(){
        return "admin only!";
    }
//    @RequestMapping("/login")
//    public String redirectTo(){
//        return "yourRedirectLink";
//    }

}
