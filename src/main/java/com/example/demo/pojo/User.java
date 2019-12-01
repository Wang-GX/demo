package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user")
public class User extends Model<User> implements Serializable {
    //可以点击IDEA左侧的Structure查看当前类的结构(属性，方法)

    @TableId(value = "id", type = IdType.AUTO)//指定id类型为数据库自增长
    private Integer id;
    @TableField(value = "user_name")
    private String userName;
    @TableField("user_sex")
    private String userSex;
    @TableField("user_age")
    private String userAge;
    @TableField(exist = false)
    private String authorName;
    @TableField(exist = false)
    private String authorHeight;

    //乐观锁版本号，如果需要使用乐观锁，确保数据库中有此字段
    @Version
    private Integer version;

}
