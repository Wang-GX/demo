package com.example.demo.多线程_本地线程锁_线程池;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocalLockController {

    @Autowired
    private LocalLockService service;

    /**
     * 本地线程锁测试
     */
    @GetMapping("/localLock")
    public void localLock(){
        service.localLockTest();
    }

}
