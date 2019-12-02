package com.example.demo.定时任务_分布式锁.Redis实现;

public interface RedisLock {

    boolean lock(long releaseTime);
    void unlock();
}