package com.example.demo.common.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends User{

    //测试属性
    private String test;
}
