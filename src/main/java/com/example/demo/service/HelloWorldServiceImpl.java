package com.example.demo.service;

import com.example.demo.config.CustomizeConfig;
import com.example.demo.config.DefaultConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldServiceImpl implements IHelloWorldService  {

    @Autowired
    private DefaultConfig defaultConfig;

    @Autowired
    private CustomizeConfig customizeConfig;

    @Value(value = "${user.userName}")
    private String userName;
    @Value("${user.sex}")
    private String sex;
    @Value("${user.age}")
    private String age;

    @Override
    public void getMessage() {
        System.out.println(defaultConfig);
    }

    @Override
    public void getMessage2() {
        System.out.println(customizeConfig);
    }
}
