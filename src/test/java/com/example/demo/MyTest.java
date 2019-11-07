package com.example.demo;

import com.example.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application.properties"})
//TODO 测试类运行失败
public class MyTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void myTest(){
        System.out.println(userMapper.getUser());
    }
}
