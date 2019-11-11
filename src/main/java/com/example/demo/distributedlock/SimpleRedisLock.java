package com.example.demo.distributedlock;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;


public class SimpleRedisLock implements RedisLock{

    private StringRedisTemplate redisTemplate;
    /**
     * 设定好锁对应的 key
     */
    private String key;
    /**
     * 锁对应的值，无意义，写为1
     */
    private static final String VALUE = "1";

    public SimpleRedisLock(StringRedisTemplate redisTemplate, String key) {
        this.redisTemplate = redisTemplate;
        this.key = key;
    }

    @Override
    public boolean lock(long releaseTime) {
        // 尝试获取锁
        Boolean boo = redisTemplate.opsForValue().setIfAbsent(key, VALUE, releaseTime, TimeUnit.SECONDS);
        // 判断结果
        return boo != null && boo;
    }

    @Override
    public void unlock(){
        // 删除key即可释放锁
        redisTemplate.delete(key);
    }
}