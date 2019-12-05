package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Controller-->声明当前接口为普通接口，如果没有添加@ResponseBody注解，则接口返回数据渲染后的页面(依据是MVC的视图解析器)。
 * @RestController-->声明当前接口为Rest风格接口，接口返回json数据而非渲染后的页面。此时视图解析器不会生效。
 */
@RestController
@RequestMapping("/common/controller")
public class CommonController {

    @PostMapping("/test")
    public void springAopTest() {
        System.out.println(1 / 0);
        System.out.println("测试Spring AOP");
    }
}
