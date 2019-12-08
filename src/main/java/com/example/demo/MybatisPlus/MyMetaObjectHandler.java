package com.example.demo.MybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

public class MyMetaObjectHandler implements MetaObjectHandler {

    //插入数据时自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        //先获取插入数据时传入的值
        Object userSex = getFieldValByName("userSex", metaObject);
        //如果为null，就填充默认值
        if (userSex == null) {
            setFieldValByName("userSex", "1", metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
