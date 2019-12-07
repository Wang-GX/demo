package com.example.demo.common.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.MybatisPlus.MyBaseMapper;
import com.example.demo.common.pojo.User;
import org.apache.ibatis.annotations.Param;

//@DS("master")
@DS("slave_1")
public interface UserMapper extends MyBaseMapper<User> {

    User getUser();

    void insertUser(@Param("user") User user);
}
