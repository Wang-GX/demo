package com.example.demo.service;

import com.example.demo.pojo.User;

public interface MybatisService {

    User getUser();

    User insertUser(User user);
}
