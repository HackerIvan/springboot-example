package com.kingboy.axon.customer.query;

import com.kingboy.axon.customer.event.CustomerCreatedEvent;
import com.kingboy.axon.customer.event.CustomerDepositedEvent;
import com.kingboy.axon.customer.event.CustomerWithdrawedEvent;
import com.kingboy.axon.customer.event.OrderPaidEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 15:00
 * Desc
 */
@Component
public class CustomerProjector {

    @Resource
    CustomerRepository customerRepository;

    @EventHandler
    public void on(CustomerCreatedEvent event) {
        CustomerEntity customerEntity = new CustomerEntity(event.getCustomerId(), event.getUsername(),
                event.getPassword(), 0d);
        customerRepository.save(customerEntity);
    }

    @EventHandler
    public void on(CustomerDepositedEvent event) {
        Optional<CustomerEntity> customerEntity = customerRepository.findByCustomerId(event.getCustomerId());
        customerEntity.ifPresent(p -> p.setAmount(p.getAmount() + event.getAmount()));
    }

    @EventHandler
    public void on(CustomerWithdrawedEvent event) {
        Optional<CustomerEntity> customerEntity = customerRepository.findByCustomerId(event.getCustomerId());
        customerEntity.ifPresent(p -> p.setAmount(p.getAmount() - event.getAmount()));
    }

    @EventHandler
    public void on(OrderPaidEvent event) {
        Optional<CustomerEntity> customerEntity = customerRepository.findByCustomerId(event.getCustomerId());
        customerEntity.ifPresent(p -> p.setAmount(p.getAmount() - event.getAmount()));
    }

}
