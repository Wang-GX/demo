package com.example.demo.动态代理AOP;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
    public Object execute1(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        logger.info(methodName + "方法开始执行，入参：" + JSONObject.toJSONString(joinPoint.getArgs(), SerializerFeature.WriteMapNullValue));
        Object proceed = joinPoint.proceed();
        logger.info(methodName + "方法执行完毕，出参：：" + JSONObject.toJSONString(proceed, SerializerFeature.WriteMapNullValue));
        return proceed;
    }
}
