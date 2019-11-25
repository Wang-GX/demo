package com.example.demo.MybatisPlusTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MybatisPlusTest {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void showSqlSessionFactory() {

        //定义的SQL语句，最终会被解析成statement对象注入到Mybatis容器中。
        //在下面这行代码处打断点，查看mappedStatements属性值。
        System.out.println(sqlSessionFactory);
    }

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据id查询
     */
    @Test
    void testSelectById() {
        User user = userMapper.selectById(1);
        System.out.println("查询到的数据：" + user);
    }

    /**
     * 根据Ids批量查询
     */
    @Test
    void testSelectByIds() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        List<User> users = userMapper.selectBatchIds(ids);
        System.out.println("查询到的数据：" + users);
    }

    /**
     * 根据条件查询一条数据(如果有多条数据符合查询条件则会报错)
     */
    @Test
    void testSelectOne() {
        //构建查询条件，多个条件之间是and关系
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "zhangsan").eq("user_sex", 1);
        User user = userMapper.selectOne(wrapper);
        System.out.println("查询到的数据：" + user);
    }

    /**
     * 根据条件查询数据的数量
     */
    @Test
    void testSelectCount() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "zhangsan");
        Integer count = userMapper.selectCount(wrapper);
        System.out.println("查询到的数据：" + count);
    }

    /**
     * 根据条件查询多条数据
     */
    @Test
    void testSelectList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "zhangsan");
        List<User> users = userMapper.selectList(wrapper);
        System.out.println("查询到的数据：" + users);
    }

    /**
     * 分页查询
     */
    @Test
    void testSelectPage() {
        Page<User> page = new Page<>(1, 1);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "zhangsan");
        IPage<User> iPage = this.userMapper.selectPage(page, wrapper);
        System.out.println("数据总条数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("当前页数：" + iPage.getCurrent());
        List<User> records = iPage.getRecords();
        System.out.println("查询到的数据：" + records);
    }


    /**
     * 新增
     */
    @Test
    void testInsert() {
        User user = new User();
        user.setUserName("mybatisPlus");
        user.setUserSex("1");
        user.setUserAge("10");
        int count = this.userMapper.insert(user);
        System.out.println("数据库受影响的行数为：" + count);
        //主键回显
        System.out.println(user.getId());
    }

    /**
     * 根据id更新
     */
    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(1);
        user.setUserAge("100");
        int count = this.userMapper.updateById(user);
        System.out.println("数据库受影响的行数为：" + count);
    }

    /**
     * 根据条件更新
     */
    @Test
    void testUpdateByCondition() {
        User user = new User();
        user.setUserAge("100");

        //构建条件：某个字段的值
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "mybatisPlus");//TODO 注意：参数都为字段名和字段值，而不是属性名
        int count = this.userMapper.update(user, wrapper);
        System.out.println("数据库受影响的行数为：" + count);

    }

    /**
     * 根据id删除
     */
    @Test
    void testDeleteById() {
        int count = this.userMapper.deleteById(1);
        System.out.println("数据库受影响的行数为：" + count);
    }

    /**
     * 根据Ids批量删除
     */
    @Test
    void testDeleteByIds() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        int count = this.userMapper.deleteBatchIds(ids);
        System.out.println("数据库受影响的行数为：" + count);
    }

    /**
     * 根据条件删除
     */
    @Test
    void testDeleteByCondition1() {
        //多个条件之间是and关系
        Map<String, Object> map = new HashMap<>();
        map.put("user_name", "zhangsan");
        map.put("user_sex", 1);
        int count = this.userMapper.deleteByMap(map);
        System.out.println("数据库受影响的行数为：" + count);
    }

    /**
     * 根据条件删除
     */
    @Test
    void testDeleteByCondition2() {
        User user = new User();
        user.setUserName("zhangsan");
        user.setUserSex("1");
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        int count = this.userMapper.delete(wrapper);
        System.out.println("数据库受影响的行数为：" + count);
    }


}
