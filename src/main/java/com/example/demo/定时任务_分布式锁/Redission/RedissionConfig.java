package com.example.demo.定时任务_分布式锁.Redission;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RedissionConfig {

    @Bean
    public Redisson redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379").setDatabase(0);
        /*config.useClusterServers()
                .addNodeAddress("redis://localhost:8081")
                .addNodeAddress("redis://localhost:8082")
                .addNodeAddress("redis://localhost:8083")
                .addNodeAddress("redis://localhost:8084");*/
        return (Redisson) Redisson.create(config);
    }
}

