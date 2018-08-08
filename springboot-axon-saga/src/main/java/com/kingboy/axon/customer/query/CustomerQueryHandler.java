package com.kingboy.axon.customer.query;

import com.kingboy.axon.customer.Customer;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 17:09
 * Desc
 */
@Component
public class CustomerQueryHandler {

    @Resource
    AxonConfiguration axonConfiguration;

    // WARN: 强烈不建议使用这种方式将聚合数据暴露给外界，而应该使用物化视图的方式将保存的视图数据显示出来。
    // 这里这样做，只是用于debug，有时候，可能写的代码有问题，导致聚合数据跟视图数据不一致。
    @QueryHandler
    public Customer query(String customerId) {
        final Customer[] customers = new Customer[1];
        Repository<Customer> repository = axonConfiguration.repository(Customer.class);
        repository.load(customerId).execute(p -> customers[0] = p);
        return customers[0];
    }

}
