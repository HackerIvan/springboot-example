package com.kingboy.axon.order;

import com.kingboy.axon.customer.command.OrderPayCommand;
import com.kingboy.axon.customer.event.OrderPaidEvent;
import com.kingboy.axon.customer.event.OrderPaidFailEvent;
import com.kingboy.axon.order.command.OrderFailCommand;
import com.kingboy.axon.order.command.OrderFinishCommand;
import com.kingboy.axon.order.event.OrderCreatedEvent;
import com.kingboy.axon.order.event.OrderFailedEvent;
import com.kingboy.axon.order.event.OrderFinishedEvent;
import com.kingboy.axon.ticket.command.TicketLockCommand;
import com.kingboy.axon.ticket.command.TicketMoveCommand;
import com.kingboy.axon.ticket.command.TicketUnlockCommand;
import com.kingboy.axon.ticket.event.TicketLockedEvent;
import com.kingboy.axon.ticket.event.TicketLockedFailEvent;
import com.kingboy.axon.ticket.event.TicketMovedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.ScheduleToken;
import org.axonframework.spring.stereotype.Saga;

import javax.annotation.Resource;
import java.time.Instant;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/31 14:01
 * Desc
 */
@Saga
@Slf4j
@NoArgsConstructor
public class OrderCreateManagement {

    @Resource
    private transient CommandBus commandBus;

    @Resource
    transient EventScheduler eventScheduler;

    private String ticketId;

    private String orderId;

    private String customerId;

    private Double amount;

    private ScheduleToken token;
    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void startOrder(OrderCreatedEvent event) {
        this.ticketId = event.getTicketId();
        this.orderId = event.getOrderId();
        this.customerId = event.getCustomerId();
        this.amount = event.getAmount();

        token = eventScheduler.schedule(Instant.now().plusSeconds(30), new OrderFailedEvent(orderId, "超时"));

        TicketLockCommand command = new TicketLockCommand(event.getTicketId(), event.getCustomerId(), event.getOrderId());
        commandBus.dispatch(asCommandMessage(command));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(TicketLockedEvent event) {
        OrderPayCommand command = new OrderPayCommand(customerId, orderId, amount);
        commandBus.dispatch(asCommandMessage(command));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderPaidEvent event) {
        TicketMoveCommand command = new TicketMoveCommand(ticketId, customerId, orderId);
        commandBus.dispatch(asCommandMessage(command));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(TicketMovedEvent event) {
        OrderFinishCommand command = new OrderFinishCommand(orderId);
        commandBus.dispatch(asCommandMessage(command));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderFinishedEvent event) {
        log.info("order {} finished", orderId);
        if (this.token != null) {
            eventScheduler.cancelSchedule(token);
        }

    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(TicketLockedFailEvent event) {
        OrderFailCommand command = new OrderFailCommand(orderId, event.getReason());
        commandBus.dispatch(asCommandMessage(command));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderFailedEvent event) {
        log.error("order {} failed", orderId);
        if (this.token != null) {
            eventScheduler.cancelSchedule(token);
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderPaidFailEvent event) {
        OrderFailCommand command = new OrderFailCommand(orderId, event.getReason());
        commandBus.dispatch(asCommandMessage(command));

        TicketUnlockCommand unlockCommand = new TicketUnlockCommand(ticketId, customerId);
        commandBus.dispatch(asCommandMessage(unlockCommand));
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
