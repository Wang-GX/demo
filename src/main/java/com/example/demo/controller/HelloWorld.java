package com.example.demo.controller;

import com.example.demo.service.IHelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloWorld {

    @Autowired
    private IHelloWorldService helloWorldService;

    /**
     * 读取默认配置文件application-{profile}.properties
     * @return
     */
    @RequestMapping(value = "default")
    public void HelloWorld(){
        helloWorldService.getMessage();
    }

    /**
     * 读取自定义配置文件my.properties
     * @return
     */
    @RequestMapping(value = "customize")
    public void HelloWorld2(){
        helloWorldService.getMessage2();
    }

}
