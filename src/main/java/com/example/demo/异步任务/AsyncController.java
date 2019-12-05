package com.example.demo.异步任务;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private AsyncMethod asyncMethod;

    @RequestMapping("/test")
    public void asyncTest() throws InterruptedException {
        System.out.println("当前线程名称：" + Thread.currentThread().getName());
        asyncMethod.asyncMethod();
        System.out.println("主线程执行完毕");
    }

}
