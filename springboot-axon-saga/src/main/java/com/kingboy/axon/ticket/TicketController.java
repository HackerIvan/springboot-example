package com.kingboy.axon.ticket;

import com.kingboy.axon.ticket.command.TicketCreateCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/31 01:16
 * Desc
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Resource
    CommandGateway commandGateway;

    @PostMapping("/name/{name}")
    public void create(@PathVariable String name) {
        String ticketId = UUID.randomUUID().toString().replaceAll("-", "");
        commandGateway.send(new TicketCreateCommand(ticketId, name));
    }

}
