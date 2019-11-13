package com.example.demo.SpringJavaConfig配置Bean;

import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    @Qualifier("userByDefaultConfig")
    private User userByDefaultConfig;

    @Autowired
    @Qualifier("userByCustomizeConfig")
    private User userByCustomizeConfig;

    @Override
    public void getUserByDefaultConfig() {
        System.out.println(userByDefaultConfig);
    }

    @Override
    public void getUserByCustomizeConfig() {
        System.out.println(userByCustomizeConfig);
    }
}
