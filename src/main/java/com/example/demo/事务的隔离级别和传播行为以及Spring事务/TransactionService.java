package com.example.demo.事务的隔离级别和传播行为以及Spring事务;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TransactionService transactional;

    @Transactional
    public void aTransactional() {
        User user1 = new User();
        user1.setUserName("张三");
        user1.setUserAge("18");
        userMapper.insertUser(user1);
        try {
            transactional.bTransactional();//TODO !!! 如果A和B位于同一个类，不要使用this(或直接)调用，而是应该通过注入本类对象调用。否则会出现与预期结果不符的情况。
        } catch (Exception e) {
            System.out.println("B中出现异常，回滚B");
        }
        //transactional.bTransactional();
    }

    /**
     * REQUIRED(Spring默认的传播行为)
     * 如果存在当前事务，则在当前事务中执行。否则开启一个新的事务执行。
     * B单独执行，则开启B事务执行。A调用B，则B和A都在A事务中执行。
    */
    @Transactional(propagation = Propagation.REQUIRED)//REQUIRED

    /**
     * REQUIRES_NEW
     * 无论是否存在当前事务，都开启一个新的事务执行。
     * B单独执行，则开启B事务执行。A调用B，则开启B事务执行B，此时如果有A事务，则A事务挂起。
     */
    //@Transactional(propagation = Propagation.REQUIRES_NEW)//REQUIRES_NEW
    public void bTransactional() {
        User user2 = new User();
        user2.setUserName("李四");
        user2.setUserAge("14");
        userMapper.insertUser(user2);
        try {
            System.out.println(1 / 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
/*
 * 事务方法A调用事务方法B
 * REQUIRED：
 * B异常，捕获，B和A都正常提交。
 * B异常，未捕获，B和A都回滚(无论A中是否捕获)。
 *
 * REQUIRES_NEW：
 * B异常，未捕获，A捕获，B回滚，A正常提交。
 * B异常，未捕获，A未捕获，B和A都回滚。
 * B异常，捕获，B和A都正常提交。
 *
 * */

//添加事务@Transactional(rollbackFor = Exception.class) 不管检查异常还是非检查异常都会回滚
//事务方法必须声明为public，否则事务失效