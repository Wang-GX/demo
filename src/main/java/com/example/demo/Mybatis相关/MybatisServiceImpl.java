package com.example.demo.Mybatis相关;

import com.example.demo.common.mapper.UserMapper;
import com.example.demo.common.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MybatisServiceImpl implements MybatisService {

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
    public void insertUser(User user) {
        this.userMapper.insertUser(user);
        System.out.println(user);
    }

    @Override
    public List<User> testIf(User user) {
        return this.userMapper.testIf(user);
    }

    @Override
    public List<User> testChoose(User user) {
        return this.userMapper.testChoose(user);
    }

    @Override
    public List<User> testWhere(User user) {
        return this.userMapper.testWhere(user);
    }

    @Override
    public Integer testSet(User user) {
        return this.userMapper.testSet(user);
    }

    @Override
    public List<User> testForEach(List<Integer> userAgeList) {
        return this.userMapper.testForEach(userAgeList);
    }
}
