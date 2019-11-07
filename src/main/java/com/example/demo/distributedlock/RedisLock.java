package com.example.demo.distributedlock;

public interface RedisLock {
    boolean lock(long releaseTime);
    void unlock();
}