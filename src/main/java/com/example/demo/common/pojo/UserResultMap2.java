package com.example.demo.common.pojo;

import lombok.Data;

import java.util.List;

@Data
public class UserResultMap2 {

    //测试一对多
    private List<User> userList;
}
