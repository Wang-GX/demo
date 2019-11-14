package com.example.demo.Mybatis相关;

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
//        userMapper.getUser();
//        userMapper.getUser();
//        userMapper.getUser();
//        System.out.println(userMapper);
        User user = this.userMapper.getUser();
        System.out.println(user);
        return user;
    }

    @Override
    public User insertUser(User user) {
        this.userMapper.insertUser(user);
        System.out.println(user);
        return user;
    }

}
