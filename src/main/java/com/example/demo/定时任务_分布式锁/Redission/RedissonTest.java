package com.example.demo.定时任务_分布式锁.Redission;

import com.example.demo.common.mapper.UserMapper;
import com.example.demo.common.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedissonTest {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private UserMapper userMapper;


    //@Scheduled(cron = "*/3 * * * * ?")
    public void execute1() {
        //获取锁，设置锁的自动失效时间为2分钟
        RLock mylock = redissonClient.getLock("mylock");

        //判断是否获取锁
        if (mylock.isLocked()) {
            //获取锁失败
            log.info("execute1放弃执行");
        } else {
            //获取锁成功，加锁，执行业务
            mylock.lock(2, TimeUnit.MINUTES);//设置key的存活时间，过期自动释放锁
            try {
                log.info("execute1执行");
                User user = new User();
                user.setUserName(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                this.userMapper.insertUser(user);
                //执行成功，手动释放锁
                mylock.unlock();
                log.info("execute1释放锁");
            } catch (Exception e) {
                log.info("execute1执行异常" + e);
            }
        }
    }


    //@Scheduled(cron = "*/3 * * * * ?")
    public void execute2() {
        //获取锁，设置锁的自动失效时间为2分钟
        RLock mylock = redissonClient.getLock("mylock");

        //判断是否获取锁
        if (mylock.isLocked()) {
            //获取锁失败
            log.info("execute2放弃执行");
        } else {
            //获取锁成功，加锁，执行业务
            mylock.lock(2, TimeUnit.MINUTES);//设置key的存活时间，过期自动释放锁
            try {
                log.info("execute2执行");
                User user = new User();
                user.setUserName(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                this.userMapper.insertUser(user);
                //执行成功，手动释放锁
                mylock.unlock();
                log.info("execute2释放锁");
            } catch (Exception e) {
                log.info("execute2执行异常" + e);
            }
        }
    }

    //@Scheduled(cron = "*/3 * * * * ?")
    public void execute4() {
        //获取锁，设置锁的自动失效时间为2分钟
        RLock mylock = redissonClient.getLock("mylock");

        //判断是否获取锁
        if (mylock.isLocked()) {
            //获取锁失败
            log.info("execute3放弃执行");
        } else {
            //获取锁成功，加锁，执行业务
            mylock.lock(2, TimeUnit.MINUTES);//设置key的存活时间，过期自动释放锁
            try {
                log.info("execute3执行");
                User user = new User();
                user.setUserName(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                this.userMapper.insertUser(user);
                //执行成功，手动释放锁
                mylock.unlock();
                log.info("execute3释放锁");
            } catch (Exception e) {
                log.info("execute3执行异常" + e);
            }
        }
    }

    //@Scheduled(cron = "*/3 * * * * ?")
    public void execute5() {
        //获取锁，设置锁的自动失效时间为2分钟
        RLock mylock = redissonClient.getLock("mylock");

        //判断是否获取锁
        if (mylock.isLocked()) {
            //获取锁失败
            log.info("execute5放弃执行");
        } else {
            //获取锁成功，加锁，执行业务
            mylock.lock(2, TimeUnit.MINUTES);//设置key的存活时间，过期自动释放锁
            try {
                log.info("execute5执行");
                User user = new User();
                user.setUserName(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                this.userMapper.insertUser(user);
                //执行成功，手动释放锁
                mylock.unlock();
                log.info("execute5释放锁");
            } catch (Exception e) {
                log.info("execute5执行异常" + e);
            }
        }
    }

    //@Scheduled(cron = "*/3 * * * * ?")
    public void execute6() {
        //获取锁，设置锁的自动失效时间为2分钟
        RLock mylock = redissonClient.getLock("mylock");

        //判断是否获取锁
        if (mylock.isLocked()) {
            //获取锁失败
            log.info("execute6放弃执行");
        } else {
            //获取锁成功，加锁，执行业务
            mylock.lock(2, TimeUnit.MINUTES);//设置key的存活时间，过期自动释放锁
            try {
                log.info("execute6执行");
                User user = new User();
                user.setUserName(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                this.userMapper.insertUser(user);
                //执行成功，手动释放锁
                mylock.unlock();
                log.info("execute6释放锁");
            } catch (Exception e) {
                log.info("execute6执行异常" + e);
            }
        }
    }

    //@Scheduled(cron = "*/3 * * * * ?")
    public void execute7() {
        //获取锁，设置锁的自动失效时间为2分钟
        RLock mylock = redissonClient.getLock("mylock");

        //判断是否获取锁
        if (mylock.isLocked()) {
            //获取锁失败
            log.info("execute7放弃执行");
        } else {
            //获取锁成功，加锁，执行业务
            mylock.lock(2, TimeUnit.MINUTES);//设置key的存活时间，过期自动释放锁
            try {
                log.info("execute7执行");
                User user = new User();
                user.setUserName(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                this.userMapper.insertUser(user);
                //执行成功，手动释放锁
                mylock.unlock();
                log.info("execute7释放锁");
            } catch (Exception e) {
                log.info("execute7执行异常" + e);
            }
        }
    }

    //@Scheduled(cron = "*/3 * * * * ?")
    public void execute8() {
        //获取锁，设置锁的自动失效时间为2分钟
        RLock mylock = redissonClient.getLock("mylock");

        //判断是否获取锁
        if (mylock.isLocked()) {
            //获取锁失败
            log.info("execute8放弃执行");
        } else {
            //获取锁成功，加锁，执行业务
            mylock.lock(2, TimeUnit.MINUTES);//设置key的存活时间，过期自动释放锁
            try {
                log.info("execute8执行");
                User user = new User();
                user.setUserName(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                this.userMapper.insertUser(user);
                //执行成功，手动释放锁
                mylock.unlock();
                log.info("execute8释放锁");
            } catch (Exception e) {
                log.info("execute8执行异常" + e);
            }
        }
    }

    //@Scheduled(cron = "*/3 * * * * ?")
    public void execute9() {
        //获取锁，设置锁的自动失效时间为2分钟
        RLock mylock = redissonClient.getLock("mylock");

        //判断是否获取锁
        if (mylock.isLocked()) {
            //获取锁失败
            log.info("execute9放弃执行");
        } else {
            //获取锁成功，加锁，执行业务
            mylock.lock(2, TimeUnit.MINUTES);//设置key的存活时间，过期自动释放锁
            try {
                log.info("execute9执行");
                User user = new User();
                user.setUserName(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                this.userMapper.insertUser(user);
                //执行成功，手动释放锁
                mylock.unlock();
                log.info("execute9释放锁");
            } catch (Exception e) {
                log.info("execute9执行异常" + e);
            }
        }
    }

    //@Scheduled(cron = "*/3 * * * * ?")
    public void execute3() {
        RLock mylock = redissonClient.getLock("mylock");
        System.out.println(mylock);
        //isLocked()方法用来判断该key是否存在，如果存在则返回true，如果不存在则返回false
        System.out.println(mylock.isLocked());
        mylock.lock(5, TimeUnit.MINUTES);
    }
}
