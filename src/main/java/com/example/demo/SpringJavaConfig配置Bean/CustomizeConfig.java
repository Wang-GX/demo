package com.example.demo.SpringJavaConfig配置Bean;

import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 读取自定义配置文件
 * 需要在配置类中指定自定义配置文件的位置，通过@PropertySource注解
 */
@Configuration
@PropertySource(value = {"classpath:my.properties","classpath:my2.properties"})//TODO 指定自定义配置文件的位置，可以指定多个
@ConfigurationProperties(prefix = "my")//指定前缀批量注入
public class CustomizeConfig {

    @Value("${my2.userName}")//指定具体值单独注入
    private String userName;

    @Value("${my.userSex}")
    private String userSex;

    @Value("${my.userAge}")
    private String userAge;

    //TODO 通过前缀+属性名注入，设置的属性必须要有set方法!!!(这里指的是配置类)，通过调用set方法注入author.name属性值
    private String authorName;

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }


    @Value("${my.authorHeight2}")
    //TODO 通过@Value注入，属性名任意
    //通过@Value注入my.authorHeight2属性值，然后调用set方法注入my.authorHeight属性值，所以最终的值为my.authorHeight的属性值[通过debug观察]
    private String authorHeight;

    public void setAuthorHeight(String authorHeight) {
        this.authorHeight = authorHeight;
    }

    @Bean("userByCustomizeConfig")
    public User getUser(){
        User user = new User();
        user.setUserName(userName);
        user.setUserSex(userSex);
        user.setUserAge(userAge);
        user.setAuthorName(authorName);
        user.setAuthorHeight(authorHeight);
        return user;
    }

}
