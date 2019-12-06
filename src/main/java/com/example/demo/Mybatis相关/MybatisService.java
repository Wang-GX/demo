package com.example.demo.Mybatis相关;

import com.example.demo.common.pojo.User;

public interface MybatisService {

    User getUser();

    User insertUser(User user);

}
