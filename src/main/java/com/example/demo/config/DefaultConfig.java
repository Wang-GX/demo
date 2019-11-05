package com.example.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取格式为application-{profile}.properties的配置文件
 * 不需要在配置类中指定默认配置文件的位置(在application.properties中已指定)
 */
@Component
@ConfigurationProperties(prefix = "author")//指定前缀
@Data
public class DefaultConfig {

    //前缀+属性名的设置，设置的属性要有set和get方法，通过调用set方法注入author.name属性值
    private String name;

    @Value("${author.height2}")
    //通过@Value注入author.height2属性值，然后调用set方法注入author.height属性值，所以最终的值为author.height属性值[通过debug观察]
    private String height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
