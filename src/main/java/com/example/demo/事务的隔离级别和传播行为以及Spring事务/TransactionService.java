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
    public void aTransactional(){
        User user1 = new User();
        user1.setUserName("张三");
        user1.setUserAge("18");
        userMapper.insertUser(user1);
        try {
            bTransactional();//TODO !!! 如果A和B位于同一个类，不要使用this(或直接)调用，而是应该通过注入德本类对象调用。否则会出现与预期结果不符的情况。
        } catch (Exception e) {
            System.out.println("B中出现异常，回滚B");
        }
    }

    /**
     * REQUIRED(Spring默认的传播行为)
     * 如果存在当前事务，则在当前事务中执行。否则开启一个新的事务执行。
     * B单独执行，则开启B事务执行。A调用B，则B和A都在A事务中执行。
     * 如果B中出现异常，由于B和A处于同一事务中，所以B和A都会回滚。
     */
    //@Transactional(propagation = Propagation.REQUIRED)//REQUIRED


    /**
     *
     * 无论是否存在当前事务，都开启一个新的事务执行。
     * B单独执行，则开启B事务执行。A调用B，则开启B事务执行B，此时如果有A事务，则A事务挂起。
     * 如果B中出现异常，则B回滚，A不受影响。如果A中出现异常，则A回滚，B不受影响。
     * TODO 注意：如果在A中没有捕获异常，A中会出现异常，此时B和A中都出现了异常，所以都会回滚
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)//REQUIRES_NEW
    public void bTransactional(){
        User user2 = new User();
        user2.setUserName("李四");
        user2.setUserAge("14");
        userMapper.insertUser(user2);
        System.out.println(1/0);
    }


}
