package com.example.demo.distributedlock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.*;

@Service
public class DistributedLockService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 本地线程锁测试
     * 测试说明：模拟卖票，本地创建两个线程共享100张票。如果不出现超卖或者多个线程卖同一张票的情况则表示锁有效。
     */
    public void localLockTest(){

        //创建线程池

        /**
         *  线程池的7个核心参数：
         *
         *  int corePoolSize,  　　　　　　　　　　　　　　//核心池的大小。
         *  int maximumPoolSize,　　                  //池中允许的最大线程数，这个参数表示了线程池中最多能创建的线程数量
         *  long keepAliveTime,　　　　　　　　　　     //当线程数大于corePoolSize时，终止前多余的空闲线程等待新任务的最长时间
         *  TimeUnit unit,　　　　　　　　　　　　　    //keepAliveTime时间单位
         *  BlockingQueue<Runnable> workQueue,    //存储还没来得及执行的任务
         *  ThreadFactory threadFactory,　　　　  //执行程序创建新线程时使用的工厂
         *  RejectedExecutionHandler handler   //由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序
         */

        /**
         * corePoolSize与maximumPoolSize举例理解
         *
         * 1、池中线程数小于corePoolSize，新任务都不排队而是直接添加新线程
         * 2、池中线程数大于等于corePoolSize，workQueue未满，首选将新任务加入workQueue而不是添加新线程
         * 3、池中线程数大于等于corePoolSize，workQueue已满，但是线程数小于maximumPoolSize，添加新的线程来处理被添加的任务
         * 4、池中线程数大于大于corePoolSize，workQueue已满，并且线程数大于等于maximumPoolSize，新任务被拒绝，使用handler处理被拒绝的任务
         */

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("线程%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(
                5,
                10,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy()
        );

        //创建线程任务
        Work work = new Work();
        //线程执行线程任务
        singleThreadPool.execute(work);
        singleThreadPool.execute(work);
        //关闭线程池
        singleThreadPool.shutdown();

    }



    /**
     * 分布式锁测试
     * 测试说明：本地执行两个完全相同的定时任务，模拟集群中的两个节点同时调用某一个接口。如果在总时间t内两个定时任务的执行次数总和= t/定时任务执行间隔 则表示锁有效
     *           如：定时任务每隔5s执行一次，则在60s内，无论同时开启多少个完全相同的定时任务，最终执行的次数总和=12
     *           TODO 注意：由于定时任务本地不会出现线程安全问题，所以忽略本地线程锁。
     */
    public void distributedLockTest() throws InterruptedException {
        //1. 判断锁是否被使用，如果被使用则当前线程等待，否则进入需要同步的代码块。
        //2. 锁住整个同步代码块：在redis中设置唯一key(锁)，以及失效时间(锁的最大持有时间，防止某个线程一直持有锁导致死锁)。
        //3. 当前线程执行完毕，删除redis中设置的唯一key(释放锁)，此时等待的线程可以进入同步代码块。



    }

}

class Work implements Runnable {

    private int ticket = 100;

    /*public synchronized void sell() {
        if (this.ticket > 0) {
            try {
                Thread.sleep(1000);//sleep方法不释放锁

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "卖了第" + ticket + "张票");
            ticket--;
        }
    }*/

    @Override
    public void run() {
        while (true) {

//            sell();
            synchronized (Lock.getLock()) {
                if (this.ticket > 0) {
                    try {
                        Thread.sleep(1000);//sleep方法不释放锁

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "卖了第" + ticket + "张票");
                    ticket--;
                } else {
                    break;
                }
            }
        }
    }
}

class Lock {
    private Lock() {

    }

    private static Lock lock = new Lock();

    public static Lock getLock() {
        return lock;
    }
}