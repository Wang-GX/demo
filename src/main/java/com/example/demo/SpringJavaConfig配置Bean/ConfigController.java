package com.example.demo.SpringJavaConfig配置Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * 读取默认配置文件application-{profile}.properties
     * @return
     */
    @RequestMapping(value = "default")
    public void getUserByDefaultConfig(){
        configService.getUserByDefaultConfig();
    }

    /**
     * 读取自定义配置文件my.properties
     * @return
     */
    @RequestMapping(value = "customize")
    public void getUserByCustomizeConfig(){
        configService.getUserByCustomizeConfig();
    }

}
