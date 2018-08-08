package com.kingboy.service;

import com.kingboy.domain.User;
import com.kingboy.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/27 15:51
 * Desc 用户服务
 */
@Service
public class UserServiceWithCode {

    @Resource
    UserRepository userRepository;

    @Resource
    PlatformTransactionManager platformTransactionManager;

    public void create(User user) {
        //获取默认事物
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //设置隔离级别与事物传播机制
        def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        //如果是SUPPORT,即使在下面的模拟出错后，还是可以保存成功的，因为SUPPORT有事物可以没有也可以
        //如果想使用SUPPORT也回退，那么可以在Controller层加上@Transaction就有事物了。
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transaction = platformTransactionManager.getTransaction(def);

        try {
            userRepository.save(user);
            //模拟错误
            //int a = 1 / 0;
            platformTransactionManager.commit(transaction);
        } catch (Exception e) {
            platformTransactionManager.rollback(transaction);
            throw e;
        }


    }

}
