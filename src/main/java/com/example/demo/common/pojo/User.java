package com.example.demo.common.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class User extends Model<User> implements Serializable {
    //可以点击IDEA左侧的Structure查看当前类的结构(属性，方法)

    @TableId(value = "id", type = IdType.AUTO)//指定id类型为数据库自增长
    private Integer id;
    //使用@TableId注解标识的属性，会被MybatisPlus使用在SQL语句中
    @TableField(value = "user_name")
    private String userName;
    @TableField(value = "user_sex", fill = FieldFill.INSERT)//调用MybatisPlus的api执行insert语句时触发该属性值的自动填充
    private String userSex;
    @TableField("user_age")
    private String userAge;
    @TableField(exist = false)
    private String fatherName;
    @TableField(exist = false)
    private String fatherHeight;

    //乐观锁版本号，如果需要使用乐观锁，确保数据库中有此字段
    @Version
    private Integer version;

}

/**
 * 关于@TableField注解的理解误区：
 * 该注解确实有映射属性和字段的功能，但是并不是只有标识了该注解的属性才会和数据表中的字段完成映射。当使用@TableName注解标识类后，实体类和数据表，同名或满足驼峰匹配的属性和字段就已经完成了自动映射。
 * @TableField注解主要解决了两个问题
 *  (1)属性名和字段名不匹配(并且也不满足驼峰匹配)时无法完成自动映射，通过该注解可以将属性和字段进行手动映射。如：
 *      @TableField(value = "user_name")
 *      private String name;
 *  (2)在调用MybatisPlus的api时会自动生成SQL语句，并且只要是设置了值的属性就会被包含在生成的SQL语句中。导致的结果就是实体类中每一个使用的属性在数据表中必须存在着一个对应的字段，否则在执行SQL语句时就有可能报错。
 *     通过该注解可以忽略掉实体类中的某些属性，即使设置了值也不会被包含在生成的SQL语句中。如：
 *     @TableField(exist = false)
 *     private String fatherName;
 *
 */