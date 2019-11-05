package com.example.demo.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class User implements Serializable {
    private BigInteger id;
    private String name;
    private Integer age;
}
