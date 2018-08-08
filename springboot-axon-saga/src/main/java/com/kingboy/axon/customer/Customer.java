package com.kingboy.axon.customer;

import com.kingboy.axon.customer.command.CustomerCreateCommand;
import com.kingboy.axon.customer.command.CustomerDepositCommand;
import com.kingboy.axon.customer.command.CustomerWithdrawCommand;
import com.kingboy.axon.customer.command.OrderPayCommand;
import com.kingboy.axon.customer.event.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @AggregateIdentifier
    private String customerId;

    private String username;

    private String password;

    private Double amount;

    @CommandHandler
    public Customer(CustomerCreateCommand command) {
        apply(new CustomerCreatedEvent(command.getCustomerId(), command.getUsername(), command.getPassword()));
    }

    @EventSourcingHandler
    public void on(CustomerCreatedEvent event) {
        this.customerId = event.getCustomerId();
        this.username = event.getUsername();
        this.password = event.getPassword();
        this.amount = 0d;
        log.info("execute event : {}", event);
    }

    @CommandHandler
    public void handle(CustomerDepositCommand command) {
        apply(new CustomerDepositedEvent(command.getCustomerId(), command.getAmount()));
    }

    @EventSourcingHandler
    public void on(CustomerDepositedEvent event) {
        this.amount += event.getAmount();
        log.info("excute event :{}", event);
    }

    @CommandHandler
    public void handle(CustomerWithdrawCommand command) {
        if (command.getAmount() > this.amount) {
            log.error("withdraw {}, amount {}", command.getAmount(), this.amount);
            throw new IllegalArgumentException("取款金额大于当前余额");
        }
        apply(new CustomerWithdrawedEvent(command.getCustomerId(), command.getAmount()));
    }

    @EventSourcingHandler
    public void on(CustomerWithdrawedEvent event) {
        this.amount -= event.getAmount();
        log.info("excute event :{}", event);
    }

    @CommandHandler
    public void handle(OrderPayCommand command) {
        if (this.amount >= command.getAmount()) {
            apply(new OrderPaidEvent(command.getOrderId(), command.getCustomerId(), command.getAmount()));
        } else {
            apply(new OrderPaidFailEvent(command.getOrderId(), "amount is not enough"));
        }
    }

    @EventSourcingHandler
    public void on(OrderPaidEvent event) {
        this.amount -= event.getAmount();
    }


}
