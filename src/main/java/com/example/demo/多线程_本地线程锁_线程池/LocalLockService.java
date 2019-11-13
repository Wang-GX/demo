package com.example.demo.多线程_本地线程锁_线程池;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class LocalLockService {

    /**
     * 本地线程锁测试
     * 测试说明：模拟卖票，本地创建两个线程共享100张票。如果不出现超卖或者多个线程卖同一张票的情况则表示锁有效。
     */
    public void localLockTest() {

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

}

class Work implements Runnable {

    private int ticket = 100;

    @Override
    public void run() {
        while (true) {
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