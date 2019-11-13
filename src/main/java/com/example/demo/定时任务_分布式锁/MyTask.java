package com.example.demo.定时任务_分布式锁;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
     *
     * (2) 分布式锁(借助一台Redis服务实现)：逻辑相同，阻止多个不同节点上的线程同时执行。此时为了保证锁唯一，需要借助第三方工具redis。
     * 线程执行前先尝试从Redis中进行获取锁的操作，如果能够拿到锁则执行，否则阻塞(通过标记来判断)。锁的颗粒度最小为行。
     * 需要确保：多个服务不能同时执行被锁住的同一段代码，以及必须设置锁的超时时间，到时间自动释放，保证不会造成死锁。
     *
     * 本地的多个定时任务应该是由不同的线程控制的，理论上是同时执行的，所以可以用来测试分布式锁
     *
     * 【注意】数据库的行锁只能锁住一行数据记录的update，但是同步锁锁住的是一段代码的操作，这一段代码里可能不止包含一条操作数据库的SQL语句。前者的颗粒度更小，触发条件更苛刻，也就是说：
     * 行锁：两个线程不能同时执行一条对同一行数据记录的update操作
     * 同步锁：两个线程不能同时执行一段被锁住的代码
     *
     * (3) 实现思路：
     * 步骤：(左侧为本地线程锁，右侧为分布式锁)
     * 1. 当前线程尝试获取锁(自动)---------------->当前线程先到Redis中判断锁是否存在，如果存在则表示其他线程正在执行同步代码块中的内容，则当前线程阻塞，如果不存在则表示没有其他线程正在执行同步代码块中的代码，当前线程可以执行同步代码块中的代码。
     * 2. 当前线程进入同步代码块时锁住这段代码(自动)---------------->当前线程进入同步代码块时在Redis中设置一个锁标记(手动)，如：key：lock value：isLock，表示添加锁。
     * 3. 当前线程出同步代码块时释放锁(自动)---------------->线程出同步代码块时删除Redis中的锁标记，表示释放锁。
     * 关键点：
     * 1. 锁唯一：在Redis中设置唯一key(锁)
     * 2. 防止死锁：必须设置锁的自动失效时间，防止某个线程一直持有锁导致死锁(key设置后如果达到这个时间则自动删除，此时锁为非正常释放(某个线程执行完毕后手动删除该key为正常释放))。
     */


    /**
     * 个人理解：
     * 锁就是一个标记，这个标记对于操作它的线程必须是可见并且唯一的。
     * 在单机应用中，由于只有一个JVM进程，而堆是线程共享区，所以每个线程共享堆中的对象以及对象上的标记。因此可以通过操作任意一个唯一对象上的标记，来实现多个线程之间的同步。
     * 可以这样认为，每个对象上的标记初始值为0。当有线程进入同步代码块时，JVM将这个标记的值置为1，此时其他线程都不能进入同步代码块。当这个线程执行完毕出同步代码块时，JVM将这个标记的值置为0，此时其他线程可以进入同步代码块。
     * 而在分布式环境下，存在多个JVM进程，也就是多个堆内存。此时为了保证标记对所有线程可见且唯一，需要借助第三方工具，如Redis。将唯一的key是否存在作为这个标记的值。
     */


    /**
     * 分布式锁测试
     * 测试说明：本地执行两个完全相同的定时任务，模拟集群中的两个节点同时调用某一个接口。如果在总时间t内两个定时任务的执行次数总和= t/定时任务执行间隔，则表示分布式锁有效
     * 如：定时任务每隔5s执行一次，则在60s内，无论同时开启多少个完全相同的定时任务，最终执行的次数总和始终=12
     */

    //@Scheduled(cron = "*/3 * * * * ?")
    public void execute(){
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

        try {
            //该api方法等效于setNX 键 值 NX EX 超时时间
            //如果该key不存在则设置并返回true，如果该key已存在则不进行任何操作并返回false
            Boolean isLock = redisTemplate.opsForValue().setIfAbsent("key", new Date().toString(), 3600L, TimeUnit.SECONDS);
            if (isLock != null && isLock) {
                //获取锁
                //执行同步代码块
                {
                    log.info("执行定时任务1" + new Date() + Thread.currentThread());
                    Thread.sleep(500);//模拟任务耗时(锁是redis中的key，所以不会释放锁)
                    redisTemplate.delete("key");
                }
            } else {
                //获取锁失败，当前已有线程正在执行定时任务
                log.info(Thread.currentThread().getName() + "定时任务1放弃执行");
                //TODO 此处获取锁失败，必须return，否则会释放锁【理解错误：即使return，finally代码块也会执行，即无论是否成功获取到锁最终都会释放锁。而应该只有获取锁成功的线程才有资格释放锁。】
                //TODO 更合理的代码见execute2，这里只是为了说明一种可能发生的错误情况才写的如此繁琐。
                return;
            }
        } catch (InterruptedException e) {
            log.info("任务执行异常" + e);
            redisTemplate.delete("key");
        } finally {
            //释放锁(包括手动释放和自动释放)
            //redisTemplate.delete("key");
        }

    }

    //@Scheduled(cron = "*/3 * * * * ?")
    public void execute2(){
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
            return;
        }
        try {
            //获取锁成功
            //执行同步代码块
            {
                log.info("执行定时任务2" + new Date() + Thread.currentThread());
                Thread.sleep(500);//模拟任务耗时(锁是redis中的key，所以不会释放锁)
                redisTemplate.delete("key");
            }
        } catch (InterruptedException e) {
            log.info("任务执行异常" + e);
        } finally {
            //释放锁(包括手动释放和自动释放)
            //TODO 注意：只有进入try以后finally代码块才一定会执行，如果没有进入try则finally代码块不会执行
            redisTemplate.delete("key");
        }

    }

}