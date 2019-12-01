package com.example.demo.MybatisPlusTest;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisPluginTest {

    /**
     * 测试全表更新时阻断插件的效果
     */
    @Test
    void testAllTableUpdate(){
        User user = new User();
        user.setUserAge("20");
        //条件为null时，表示对全表进行更新
        user.update(null);
    }

    /**
     * 测试全表删除时阻断插件的效果
     */
    @Test
    void testAllTableDelete(){
        User user = new User();
        user.setUserAge("20");
        //条件为null时，表示对全表进行更新
        user.delete(null);
    }

    /**
     * 测试乐观锁插件
     * 注意：乐观锁插件只支持 updateById(id) 与 update(entity, wrapper) 方法
     */
    @Test
    void testOptimisticLock(){

        User user = new User();
        user.setId(1);//设置查询条件
        Integer oldVersion = user.selectById().getVersion();//查询数据版本号并记录
        System.out.println("oldVersion = " + oldVersion);

        System.out.println("执行逻辑代码");

        //更新前查询数据最新版本号
        user.setVersion(oldVersion);//设置查询条件
        user.setUserSex("2");//设置修改内容
        user.updateById();//执行的SQL语句会将oldVersion作为更新条件，(oldVersion + 1)作为更新的值。如果其他线程更新过数据，版本号的值也会被修改(严格递增)，那么更新条件不满足，更新失败。

        System.out.println("newVersion = " + user.getVersion());//最新的版本号会回显到实体类对象中

    }


    @Autowired
    private UserMapper userMapper;

    /**
     * 测试SQL注入器
     */
    @Test
    void testSqlInjector(){
        List<User> users = userMapper.findAll();
        System.out.println(users);
    }

}
