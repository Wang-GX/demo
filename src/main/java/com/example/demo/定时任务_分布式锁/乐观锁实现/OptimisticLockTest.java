package com.example.demo.定时任务_分布式锁.乐观锁实现;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 使用乐观锁的方式实现分布式锁
 */
@Component
public class OptimisticLockTest {

    @Autowired
    private UserMapper userMapper;

    //@Scheduled(cron = "*/10 * * * * ?")
    public void execute1() {

        //查询数据版本号并记录
        Integer oldVersion = this.userMapper.selectById(1).getVersion();//1
        System.out.println("定时任务1 oldVersion：" + oldVersion);

        //执行代码逻辑
        User user = new User();
        user.setUserSex("2");
        user.setVersion(oldVersion + 1);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",1).eq("version",oldVersion);

        //更新前查询数据最新版本号
        Integer newVersion = this.userMapper.selectById(1).getVersion();
        System.out.println("定时任务1 newVersion：" + newVersion);
        if (newVersion.equals(oldVersion)) {
            if (this.userMapper.update(user,wrapper) == 1) {
                System.out.println("定时任务1执行成功");
            } else {
                System.out.println("定时任务1执行失败");
            }
        } else {
            System.out.println("定时任务1执行失败");
        }
    }

    //@Scheduled(cron = "*/10 * * * * ?")
    public void execute2() {

        //查询数据版本号并记录
        Integer oldVersion = this.userMapper.selectById(1).getVersion();//1
        System.out.println("定时任务2 oldVersion：" + oldVersion);

        //执行代码逻辑
        User user = new User();
        user.setUserSex("2");
        user.setVersion(oldVersion + 1);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",1).eq("version",oldVersion);

        //更新前查询数据最新版本号
        Integer newVersion = this.userMapper.selectById(1).getVersion();
        System.out.println("定时任务2 newVersion：" + newVersion);
        if (newVersion.equals(oldVersion)) {
            if (this.userMapper.update(user,wrapper) == 1) {
                System.out.println("定时任务2执行成功");
            } else {
                System.out.println("定时任务2执行失败");
            }
        } else {
            System.out.println("定时任务2执行失败");
        }
    }

}
