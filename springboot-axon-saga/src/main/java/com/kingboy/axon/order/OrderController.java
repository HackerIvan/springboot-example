package com.kingboy.axon.order;

import com.kingboy.axon.order.command.OrderCtreatCommand;
import com.kingboy.axon.utils.MapperUtils;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 18:00
 * Desc
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    CommandGateway commandGateway;

    @PostMapping
    public void create(@RequestBody Order order) {
        OrderCtreatCommand orderCtreatCommand = MapperUtils.mapperBean(order, OrderCtreatCommand.class);
        orderCtreatCommand.setOrderId(UUID.randomUUID().toString().replaceAll("-", ""));
        commandGateway.send(orderCtreatCommand, LoggingCallback.INSTANCE);
    }

}
