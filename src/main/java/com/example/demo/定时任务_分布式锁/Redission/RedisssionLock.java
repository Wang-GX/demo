package com.example.demo.定时任务_分布式锁.Redission;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class RedisssionLock {

	@Autowired
    private Redisson redisson;
    
    
    public  boolean acquire(String lockName){  
        RLock mylock = redisson.getLock(lockName);
        mylock.lock(2, TimeUnit.MINUTES); //lock提供带timeout参数，timeout结束强制解锁，防止死锁  
        return  true;  
    }  
  
    public  void release(String lockName){  
        RLock mylock = redisson.getLock(lockName);
        mylock.unlock();  
    }  
}
