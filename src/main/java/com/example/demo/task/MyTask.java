package com.example.demo.task;

import com.example.demo.distributedlock.DistributedLockService;
import com.example.demo.distributedlock.SimpleRedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling//该注解也可以统一加在引导类上
@Slf4j
public class MyTask {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 说明：为集群中多个节点的定时任务加分布式锁
     * (1) 普通锁(synchronized)：锁住操作公共资源的代码，阻止多个本地线程同时执行。线程执行前先尝试进行获取锁的操作，如果能够拿到锁则执行，否则阻塞。锁的颗粒度最小为行。
     * 注意：锁必须唯一
     * (2) 分布式锁(借助一台Redis服务实现)：逻辑相同，阻止多个不同节点上的线程同时执行。此时为了保证锁唯一，需要借助第三方工具redis。
     * 线程执行前先尝试从Redis中进行获取锁的操作，如果能够拿到锁则执行，否则阻塞(通过标记来判断)。锁的颗粒度最小为行。
     * <p>
     * (3) 实现思路：线程进入同步代码块时锁住这段代码(自动)---------------->线程进入同步代码块时在Redis中设置标记值为0(手动)，如：key：lock-A value：0
     * 线程尝试获取锁(自动)---------------->线程在执行同步代码块之前先去Redis中获取锁的标记值(手动)，如果为0则表示锁已被其他线程获取，则阻塞，如果为1则表示当前锁已被释放，可以正常获取锁并执行同步代码块中的代码
     * 线程出同步代码块时释放锁(自动)---------------->线程出同步代码块时在Redis中修改标记值为1(手动)，如：key：lock-A value：1
     */

    @Autowired
    private DistributedLockService distributedLockService;

    @Scheduled(cron = "*/3 * * * * ?")
    public void execute() throws InterruptedException {
        //TODO 为定时任务加分布式锁

/*

        //创建锁对象
        SimpleRedisLock lock = new SimpleRedisLock(redisTemplate, "lock");
        //获取锁，设置锁的自动失效时间为50s
        boolean isLock = lock.lock(50);
        //判断是否获取锁
        if (!isLock) {
            //获取失败
            log.info("获取锁失败，停止定时任务");
            return;
        }
        try {
            //执行业务
            log.info("获取锁成功，执行定时任务");
            //模拟任务耗时
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.info("任务执行异常" + e);
        } finally {
            lock.unlock();
        }

*/


        try {
            //该方法等效于setNX 键 值 NX EX 超时时间
            //如果该key不存在则设置并返回true，如果该key已存在则不进行任何操作并返回false
            Boolean isLock = redisTemplate.opsForValue().setIfAbsent("key", new Date().toString(), 3600L, TimeUnit.SECONDS);
            if (isLock != null && isLock) {
                //获取锁
                //执行同步代码块
                {
                    log.info("执行定时任务" + new Date() + Thread.currentThread());
                    Thread.sleep(500);//不释放锁，模拟任务耗时
                }
            } else {
                //获取锁失败，当前已有线程正在执行定时任务
                log.info(Thread.currentThread().getName() + "放弃执行");
            }
        } catch (InterruptedException e) {
            log.info("任务执行异常" + e);
        } finally {
            //释放锁(包括手动释放和自动释放)
            redisTemplate.delete("key");
        }

    }

}