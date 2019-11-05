package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MybatisServiceImpl implements MybatisService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser() {
        return this.userMapper.getUser();
    }

    @Override
    public User insertUser(User user) {
        this.userMapper.insertUser(user);
        System.out.println(user.getId());
        return user;
    }
}
