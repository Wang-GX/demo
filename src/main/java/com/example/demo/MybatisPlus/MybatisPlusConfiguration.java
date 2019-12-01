package com.example.demo.MybatisPlus;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Component
@Slf4j
public class MybatisPlusConfiguration {

    //配置分页插件
    //TODO 必须配置，否则调用selectPage方法时得到的是错误的数据
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    //配置Mybatis自定义的拦截器(插件)
    @Bean
    public MybatisInterceptor mybatisInterceptor() {
        return new MybatisInterceptor();
    }

    //配置Mybatis阻断(用来阻断全表更新、删除的操作)
    //当配置此插件后，如果执行了全表更新、删除的操作，则会抛出异常。异常信息分别为：Prohibition of table update operation/Prohibition of full table deletion
    //TODO 注意：该插件仅适用于开发环境，防止误操作，不适用于生产环境。
    @Bean
    public SqlExplainInterceptor sqlExplainInterceptor() {
        List<ISqlParser> sqlParserList = new ArrayList<>();
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        sqlParserList.add(new BlockAttackSqlParser());
        sqlExplainInterceptor.setSqlParserList(sqlParserList);
        return sqlExplainInterceptor;
    }

    //配置Mybatis性能分析插件
    //当配置此插件后，会输出每条SQL语句的执行时间。可以设置SQL语句允许的最大执行时长，如果超过此时长则会抛出异常。异常信息为：The SQL execution time is too large, please optimize ！
    //TODO 注意：该插件仅适用于开发环境，进行SQL语句的性能优化，不适用于生产环境。
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        //设置SQL允许的最大执行时长
        performanceInterceptor.setMaxTime(1000);//单位：毫秒
        //设置是否格式化SQL 默认false
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

}
