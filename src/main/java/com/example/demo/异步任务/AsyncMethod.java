package com.example.demo.异步任务;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncMethod {

    @Async
    public void asyncMethod() throws InterruptedException {
        System.out.println("执行异步任务线程名称：" + Thread.currentThread().getName());
        Thread.sleep(4000);
        System.out.println("异步线程唤醒");
    }

}
