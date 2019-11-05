package com.example.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 读取自定义配置文件
 * 需要在配置类中指定自定义配置文件的位置
 */
@Configuration
@PropertySource(value = {"classpath:my.properties","classpath:my2.properties"})//指定自定义配置文件的位置
//@PropertySource 和 @Value 组合使用，可以将自定义属性文件中的属性变量值注入到当前类的使用@Value注解的成员变量中。
//@PropertySource 和 @ConfigurationProperties 组合使用，可以将属性文件与一个Java类绑定，将属性文件中的变量值注入到该Java类的成员变量中。
@ConfigurationProperties(prefix = "my")//指定前缀
@Data
public class CustomizeConfig {

    private String name;//相当于注入my.properties配置文件中的my.name(前缀+属性名设置)
    private String age;//相当于注入my.properties配置文件中的my.age(前缀+属性名设置)

    @Value("${my2.sex}")
    private String sex;//最终注入的是my.sex
    @Value("${my2.height}")
    private String height;//最终注入的是my.height

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
