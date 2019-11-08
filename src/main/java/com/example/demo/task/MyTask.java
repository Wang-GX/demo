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
     * 本地的多个定时任务应该是由不同的线程控制的
     *
     * (3) 实现思路：线程进入同步代码块时锁住这段代码(自动)---------------->线程进入同步代码块时在Redis中设置一个标记(手动)，如：key：lock value：随意
     * 线程尝试获取锁(自动)---------------->线程先到Redis中判断锁是否存在，如果存在则表示锁已被其他线程获取，则阻塞，如果不存在则表示当前锁已被释放，可以正常获取锁并执行同步代码块中的代码
     * 线程出同步代码块时释放锁(自动)---------------->线程出同步代码块时删除这个标记，表示释放锁
     */

    /**
     * 理解：锁就是(某个唯一对象上的)标记。在单机应用中，由于只有一个jvm进程，而堆是线程共享区，所以每个线程都可以看到堆中的这个唯一对象以及上面的标记值。所以可以通过操作这个唯一对象上的标记值，来保证多个线程之间的同步。
     *       由jvm控制这个标记的值，每个对象的标记值默认为1。当有线程进入同步代码块时，将标记值置为0，此时其他线程不能进入同步代码块。当线程执行完毕出同步代码块时，将标记值置为1，此时其他线程可以进入同步代码块。
     *       而在分布式环境下，jvm进程不是同一个，所以为了保证持有标记的对象唯一，需要借助第三方工具，如Redis。将唯一的key作为持有标记的对象，这个key是否存在就是这个标记的值。
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
                    log.info("执行定时任务1" + new Date() + Thread.currentThread());
                    Thread.sleep(500);//不释放锁，模拟任务耗时
                    redisTemplate.delete("key");
                }
            } else {
                //获取锁失败，当前已有线程正在执行定时任务
                log.info(Thread.currentThread().getName() + "定时任务1放弃执行");
                //TODO 此处获取锁失败，必须return，否则会释放锁【错误：即使return，finally代码块也会执行】
                return;
            }
        } catch (InterruptedException e) {
            log.info("任务执行异常" + e);
        } finally {
            //释放锁(包括手动释放和自动释放)
            //redisTemplate.delete("key");
        }

    }

    @Scheduled(cron = "*/3 * * * * ?")
    public void execute2() throws InterruptedException {
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
                    log.info("执行定时任务2" + new Date() + Thread.currentThread());
                    Thread.sleep(500);//不释放锁，模拟任务耗时
                    redisTemplate.delete("key");
                }
            } else {
                //获取锁失败，当前已有线程正在执行定时任务
                log.info(Thread.currentThread().getName() + "定时任务2放弃执行");
                return;
            }
        } catch (InterruptedException e) {
            log.info("任务执行异常" + e);
        } finally {
            //释放锁(包括手动释放和自动释放)
            //redisTemplate.delete("key");
        }

    }

}