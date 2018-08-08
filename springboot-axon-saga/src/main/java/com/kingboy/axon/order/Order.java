package com.kingboy.axon.order;

import com.kingboy.axon.order.command.OrderCtreatCommand;
import com.kingboy.axon.order.command.OrderFailCommand;
import com.kingboy.axon.order.command.OrderFinishCommand;
import com.kingboy.axon.order.event.OrderCreatedEvent;
import com.kingboy.axon.order.event.OrderFailedEvent;
import com.kingboy.axon.order.event.OrderFinishedEvent;
import com.kingboy.axon.utils.MapperUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @AggregateIdentifier
    private String orderId;

    private String customerId;

    private String title;

    private String ticketId;

    private Double amount;

    private OrderStatus status;

    private String reason;

    private LocalDateTime createdDate;

    @CommandHandler
    public Order(OrderCtreatCommand command) {
        OrderCreatedEvent orderCreatedEvent = MapperUtils.mapperBean(command, OrderCreatedEvent.class);
        orderCreatedEvent.setCreateDate(LocalDateTime.now());
        apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.customerId = event.getCustomerId();
        this.title = event.getTitle();
        this.ticketId = event.getTicketId();
        this.amount = event.getAmount();
        this.createdDate = event.getCreateDate();
        this.status = OrderStatus.NEW;
        log.info("execute event : {}", event);
    }

    @CommandHandler
    public void handle(OrderFailCommand command) {
        apply(new OrderFailedEvent(command.getOrderId(), command.getReason()));
    }

    @EventSourcingHandler
    public void on(OrderFailedEvent event) {
        this.reason = event.getReason();
        this.status = OrderStatus.FAILED;
    }

    @CommandHandler
    public void handle(OrderFinishCommand command) {
        apply(new OrderFinishedEvent(command.getOrderId()));
    }

    @EventSourcingHandler
    public void on(OrderFinishedEvent event) {
        this.status = OrderStatus.FINISHED;
    }

}