package com.example.demo.controller;

import com.example.demo.pojo.User;
import com.example.demo.service.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MybatisController {

    @Autowired
    @Qualifier(value = "mybatisServiceImpl")
    //@Autowired根据类型注入，@Qualifier根据Bean的名字注入
    //如果一个接口有两个及以上实现类时，@Autowired需要结合@Qualifier注解一起使用(注意：后者不能单独使用)
    //使用@Component及其衍生注解标识的Spring的Bean，默认的名字为类名(首字母小写)
    private MybatisService mybatisService;

    @PostMapping("getUser")
    public User getUser(){
        return this.mybatisService.getUser();
    }

    @PostMapping("insertUser")
    public User insertUser(@RequestBody User user){
        return this.mybatisService.insertUser(user);
    }
}
