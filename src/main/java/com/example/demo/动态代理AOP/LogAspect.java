package com.example.demo.动态代理AOP;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
/**
 * @Order注解可以指定多个通知同时作用时的执行顺序
 * 数值越小的环绕通知前置越先执行，后置越后执行。即如果环绕通知1的数值 < 环绕通知2的数值，则执行顺序为：
 *           环绕通知1前置-->环绕通知1前置-->目标方法-->环绕通知2后置-->环绕通知1后置
 * 可以看出这是就一个典型的责任链模式的顺序
 */
@Order(1)
@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);


    /**
     * AOP执行顺序说明：
     * Around-->Before--> target 正常或捕获异常 -->Around-->After-->AfterReturning
     * 异常      -->AfterThrowing
     */

    //声明切点(以方法为颗粒度)
    @Pointcut("execution(* com.example.demo.controller..*.*(..))")
    public void pointcut() {
    }

    //声明通知类型和通知逻辑以及作用范围(通知作用于切入点这一动作称为切面，见图示)
    //环绕通知和其他通知同时使用时可能会出现问题，建议使用环绕通知替代其他通知
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        logger.info(methodName + "方法开始执行，入参：" + JSONObject.toJSONString(joinPoint.getArgs(), SerializerFeature.WriteMapNullValue));
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("methodName" + "方法出现异常!");
            logger.error("异常信息为：" + throwable.getMessage());
        }
        logger.info(methodName + "方法执行完毕，出参：：" + JSONObject.toJSONString(proceed, SerializerFeature.WriteMapNullValue));
        return proceed;
    }

    //@Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

    //@After("pointcut()")
    public void after() {
        logger.info("after");
    }

    //@AfterReturning("pointcut()")
    public void afterReturning() {
        logger.info("afterReturning");
    }

    //@AfterThrowing("pointcut()")
    public void afterThrowing() {
        logger.info("afterThrowing");
    }

}
