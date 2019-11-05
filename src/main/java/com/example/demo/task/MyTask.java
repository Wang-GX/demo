package com.example.demo.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling//该注解也可以统一加在引导类上
public class MyTask {

    @Scheduled(cron = "*/5 * * * * ?")
    public void execute(){
        //TODO 为定时任务加分布式锁
        System.out.println("定时任务每隔5s执行一次");
    }
}
