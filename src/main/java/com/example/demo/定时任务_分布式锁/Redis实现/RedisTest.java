package com.example.demo.定时任务_分布式锁.Redis实现;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@EnableScheduling
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Scheduled(cron = "*/1 * * * * ?")
    public void execute() {
        //TODO 为定时任务加分布式锁

        Boolean isLock = redisTemplate.opsForValue().setIfAbsent("key", new Date().toString(), 3600L, TimeUnit.SECONDS);
        //该api方法等效于setNX 键 值 NX EX 超时时间
        //如果该key不存在则设置并返回true，如果该key已存在则不进行任何操作并返回false
        if (isLock == null || !isLock) {
            //获取锁失败，当前已有线程正在执行同步代码块中的内容
            log.info(Thread.currentThread().getName() + "定时任务1放弃执行");
        } else {
            //获取锁成功
            try {
                //执行同步代码块
                {
                    log.info("执行定时任务1" + new Date() + Thread.currentThread());
                    redisTemplate.delete("key");
                    log.info("定时任务1释放锁");
                }
            } catch (Exception e) {
                log.info("定时任务1执行异常" + e);
            } finally {
                //释放锁(包括手动释放和自动释放)
                //TODO 注意：只有进入try以后finally代码块才一定会执行，如果没有进入try则finally代码块不会执行
                redisTemplate.delete("key");
            }
        }

    }

    @Scheduled(cron = "*/1 * * * * ?")
    public void execute2() {
        //TODO 为定时任务加分布式锁

/*

        //创建锁对象
        RedisLock lock = new RedisLockImpl(redisTemplate, "lock");
        //获取锁，设置锁的自动失效时间为50s
        boolean isLock = lock.lock(50);
        //判断是否获取锁
        if (!isLock) {
            //获取失败
            log.info("获取锁失败，停止定时任务");
            return;
        }
        try {
            //获取成功，执行业务
            log.info("获取锁成功，执行定时任务");
            //模拟任务耗时
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.info("任务执行异常" + e);
        } finally {
            lock.unlock();
        }


*/

        Boolean isLock = redisTemplate.opsForValue().setIfAbsent("key", new Date().toString(), 3600L, TimeUnit.SECONDS);
        //该api方法等效于setNX 键 值 NX EX 超时时间
        //如果该key不存在则设置并返回true，如果该key已存在则不进行任何操作并返回false
        if (isLock == null || !isLock) {
            //获取锁失败，当前已有线程正在执行同步代码块中的内容
            log.info(Thread.currentThread().getName() + "定时任务2放弃执行");
        } else {
            //获取锁成功
            try {
                //执行同步代码块
                {
                    log.info("执行定时任务2" + new Date() + Thread.currentThread());
                    redisTemplate.delete("key");
                    log.info("定时任务2释放锁");
                }
            } catch (Exception e) {
                log.info("定时任务2执行异常" + e);
            } finally {
                //释放锁(包括手动释放和自动释放)
                //TODO 注意：只有进入try以后finally代码块才一定会执行，如果没有进入try则finally代码块不会执行
                redisTemplate.delete("key");
            }
        }

    }

}