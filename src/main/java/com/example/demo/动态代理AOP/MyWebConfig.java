package com.example.demo.动态代理AOP;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有请求，如果项目实现了前后端分离，那么不需要考虑静态资源
        registry.addInterceptor(new MyHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/getUser");
    }

}
