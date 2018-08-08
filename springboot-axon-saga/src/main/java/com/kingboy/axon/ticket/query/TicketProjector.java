package com.kingboy.axon.ticket.query;

import com.kingboy.axon.ticket.event.TicketCreatedEvent;
import com.kingboy.axon.ticket.event.TicketLockedEvent;
import com.kingboy.axon.ticket.event.TicketMovedEvent;
import com.kingboy.axon.ticket.event.TicketUnlockedEvent;
import com.kingboy.axon.utils.MapperUtils;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/31 01:14
 * Desc
 */
@Component
public class TicketProjector {

    @Resource
    TicketRepository ticketRepository;

    @EventHandler
    public void on(TicketCreatedEvent event) {
        TicketEntity ticketEntity = MapperUtils.mapperBean(event, TicketEntity.class);
        ticketRepository.save(ticketEntity);
    }

    @EventHandler
    public void on(TicketLockedEvent event) {
        Optional<TicketEntity> ticketEntity = ticketRepository.findByTicketId(event.getTicketId());
        ticketEntity.ifPresent(p -> p.setLockUser(event.getCustomerId()));
    }

    @EventHandler
    public void on(TicketUnlockedEvent event) {
        Optional<TicketEntity> ticketEntity = ticketRepository.findByTicketId(event.getTicketId());
        ticketEntity.ifPresent(p -> p.setLockUser(null));
    }

    @EventHandler
    public void on(TicketMovedEvent event) {
        Optional<TicketEntity> ticketEntity = ticketRepository.findByTicketId(event.getTicketId());
        ticketEntity.ifPresent(p -> {
            p.setLockUser(null);
            p.setOwner(event.getCustomerId());
        });
    }

}
