package com.example.demo.common.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.common.pojo.User;

@DS("slave_2")
public interface UserMapper2 extends BaseMapper<User> {
}
