package com.example.demo.common.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.MybatisPlus.MyBaseMapper;
import com.example.demo.common.pojo.User;
import com.example.demo.common.pojo.UserResultMap1;
import com.example.demo.common.pojo.UserResultMap2;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("master")
//@DS("slave_1")
public interface UserMapper extends MyBaseMapper<User> {

    User getUser();

    void insertUser(@Param("user") User user);

    List<User> testIf(@Param("user")User user);

    List<User> testChoose(@Param("user")User user);

    List<User> testWhere(@Param("user")User user);

    Integer testSet(@Param("user")User user);

    List<User> testForEach(@Param("userAgeList")List<Integer> userAgeList);

    UserResultMap1 testResultMap1();

    UserResultMap2 testResultMap2();

}
