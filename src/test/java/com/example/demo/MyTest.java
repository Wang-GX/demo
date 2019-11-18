package com.example.demo;

import com.example.demo.SpringBoot自动配置.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Iterator;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:application.properties"})
//TODO 测试类运行失败
public class MyTest {

    private static ApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfiguration.class);

    public static void main(String[] args) {

        Weapon weapon1 = ctx.getBean(Weapon.class);
        Weapon weapon2 = ctx.getBean(Weapon.class);
        System.out.println("-------------------"+weapon1+"-------------------");
        System.out.println("-------------------"+weapon2+"-------------------");
        System.out.println(weapon1 == weapon2);
    }

    @Autowired
    //private UserMapper userMapper;

    @Test
    public void myTest(){
        //创建Spring上下文对象
        ApplicationContext ctx1 = new AnnotationConfigApplicationContext(MyConfiguration.class);
        ApplicationContext ctx2 = new AnnotationConfigApplicationContext(MyConfiguration.class);
        String[] names = ctx1.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(">>>>>>" + name);
        }
        System.out.println("------Bean 总计:" + ctx1.getBeanDefinitionCount());

        System.out.println(ctx1.getBean("game.config-com.example.demo.SpringBoot自动配置.MyProperties"));

        MyProperties bean = (MyProperties) ctx1.getBean("game.config-com.example.demo.SpringBoot自动配置.MyProperties");
        System.out.println(bean.getPersonName());
    }


        /*System.out.println(ctx1);
        System.out.println(ctx2);
        Weapon weapon = ctx1.getBean(Weapon.class);
        Cloths cloths = ctx1.getBean(Cloths.class);
        Person person = ctx1.getBean(Person.class);
        System.out.println("-------------------"+weapon+"-------------------");
        System.out.println("-------------------"+cloths+"-------------------");
        System.out.println("-------------------"+person+"-------------------");*/
    }
