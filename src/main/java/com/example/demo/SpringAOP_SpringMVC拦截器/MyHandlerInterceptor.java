package com.example.demo.SpringAOP_SpringMVC拦截器;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤器：
 * 拦截器：拦截请求--->controller
 * AOP：可以切入任意方法(连接点)
 */
public class MyHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("执行到了preHandle方法");
        System.out.println("被拦截的方法为：" + request.getMethod() + ":" + request.getRequestURI());
        return true;
    }
}
