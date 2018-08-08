package com.kingboy.axon.order.query;

import com.kingboy.axon.order.OrderStatus;
import com.kingboy.axon.order.event.OrderCreatedEvent;
import com.kingboy.axon.order.event.OrderFailedEvent;
import com.kingboy.axon.order.event.OrderFinishedEvent;
import com.kingboy.axon.utils.MapperUtils;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 18:16
 * Desc
 */
@Component
public class OrderProjector {

    @Resource
    OrderRepository orderRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderEntity orderEntity = MapperUtils.mapperBean(event, OrderEntity.class);
        orderEntity.setStatus(OrderStatus.NEW);
        orderRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderFailedEvent event) {
        Optional<OrderEntity> orderEntity = orderRepository.findByOrOrderId(event.getOrderId());
        orderEntity.ifPresent(p -> {
            p.setStatus(OrderStatus.FAILED);
            p.setReason(event.getReason());
        });
    }

    @EventHandler
    public void on(OrderFinishedEvent event) {
        Optional<OrderEntity> orderEntity = orderRepository.findByOrOrderId(event.getOrderId());
        orderEntity.ifPresent(p -> p.setStatus(OrderStatus.FINISHED));
    }

}
