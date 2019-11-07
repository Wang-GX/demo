package com.example.demo.distributedlock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistributedLockController {

    @Autowired
    private DistributedLockService service;

    /**
     * 本地线程锁测试
     */
    @GetMapping("/localLock")
    public void localLock(){
        service.localLockTest();
    }

    /**
     * 分布式锁测试
     */
    @GetMapping("/distributedLock")
    public void distributedLock() throws InterruptedException {
        service.distributedLockTest();
    }


}
