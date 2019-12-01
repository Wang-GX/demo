package com.example.demo.MybatisPlus;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
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

    //配置乐观锁插件
    //TODO 注意：乐观锁插件只支持 updateById(id) 与 update(entity, wrapper) 方法

    /**
     * 悲观锁：
     * 悲观锁是一种利用数据库内部提供的锁机制对更新的数据进行锁定。当一个线程持有了悲观锁之后，其他线程不能操作被锁定的数据。
     * 乐观锁:
     * 乐观锁不使用数据库内部提供的锁进行实现，而是使用CAS原理，所以不会阻塞其他线程，从而提高并发能力。可以通过严格递增(更新时始终递增，否则可能会出现ABA问题)的版本号来实现。
     * 线程开始执行阶段就到数据库中查询并记录下数据当前的版本号，更新时从数据库中查询最新的版本号并与记录值进行对比，如果相同则表示在此期间该数据没有被修改过，才会去更新，否则就不更新。
     * 使用乐观锁带来的问题是导致大量的更新失败，可以考虑乐观锁重入机制。
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


    //配置SQL注入器
    @Bean
    public MySqlInjector mySqlInjector() {
        return new MySqlInjector();
    }

}
