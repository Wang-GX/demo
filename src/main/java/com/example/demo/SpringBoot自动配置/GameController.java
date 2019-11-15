package com.example.demo.SpringBoot自动配置;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GameController {

    @Autowired
    private Person person;

    @PostMapping
    @RequestMapping("show")
    public void show(){
        System.out.println(person);
    }
}
