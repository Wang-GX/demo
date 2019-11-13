package com.example.demo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long id;
    private String userName;
    private String userSex;
    private String userAge;
    private String authorName;
    private String authorHeight;
}
