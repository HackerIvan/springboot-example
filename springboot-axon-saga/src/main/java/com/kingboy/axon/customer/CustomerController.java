package com.kingboy.axon.customer;

import com.kingboy.axon.customer.command.CustomerCreateCommand;
import com.kingboy.axon.customer.command.CustomerDepositCommand;
import com.kingboy.axon.customer.command.CustomerWithdrawCommand;
import com.kingboy.axon.customer.query.CustomerEntity;
import com.kingboy.axon.customer.query.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 14:40
 * Desc
 */
@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    CommandGateway commandGateway;

    @Resource
    CustomerRepository customerRepository;

    @Resource
    QueryGateway queryGateway;

    @PostMapping("/username/{username}/password/{password}")
    public CompletableFuture<Object> create(@PathVariable String username, @PathVariable String password) {
        log.info("request create customer for {}", username);
        String customerId = UUID.randomUUID().toString().replaceAll("-", "");
        return commandGateway.send(new CustomerCreateCommand(customerId, username, password));
    }

    @PutMapping("/{customerId}/deposit/{amount}")
    public CompletableFuture<Object> deposite(@PathVariable String customerId, @PathVariable Double amount) {
        log.info("request deposit {} for customer {}", amount, customerId);
        return commandGateway.send(new CustomerDepositCommand(customerId, amount));
    }

    @PutMapping("/{customerId}/withdraw/{amount}")
    public CompletableFuture<Object> withdraw(@PathVariable String customerId, @PathVariable Double amount) {
        log.info("request withdraw {} for customer {}", amount, customerId);
        return commandGateway.send(new CustomerWithdrawCommand(customerId, amount));
    }

    //根据物化视图进行查询对象，一般只暴露物化视图
    @GetMapping("/{customerId}")
    public CustomerEntity get(@PathVariable String customerId) {
        return customerRepository.findByCustomerId(customerId).orElse(new CustomerEntity());
    }

    @GetMapping("/query/{customerId}")
    public Customer getAggregate(@PathVariable String customerId) throws ExecutionException, InterruptedException {
        return queryGateway.query(customerId, Customer.class).get();
    }

}
