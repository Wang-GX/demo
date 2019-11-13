package com.example.demo.动态代理AOP;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO 过滤器、拦截器、AOP
 * 切入角度：
 * 过滤器：
 * 拦截器：拦截请求--->controller
 * AOP：可以从任意切面进行拦截，只要切入表达式的作用范围足够大。例如可以对进入controller和service的代码同时进行拦截
 */
public class MyHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("执行到了preHandle方法");
        return true;
    }
}
