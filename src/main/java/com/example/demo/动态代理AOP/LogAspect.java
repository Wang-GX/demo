package com.example.demo.动态代理AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);


    @Pointcut("execution(* com.example.demo.controller..*.*(..))")
    public void pointcut(){
    }

    @Around("pointcut()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        logger.info("方法开始执行");
        Object proceed = joinPoint.proceed();
        logger.info("方法执行完毕");
        return proceed;
    }
}
