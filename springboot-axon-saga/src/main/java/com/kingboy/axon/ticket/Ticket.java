package com.kingboy.axon.ticket;

import com.kingboy.axon.ticket.command.TicketCreateCommand;
import com.kingboy.axon.ticket.command.TicketLockCommand;
import com.kingboy.axon.ticket.command.TicketMoveCommand;
import com.kingboy.axon.ticket.command.TicketUnlockCommand;
import com.kingboy.axon.ticket.event.*;
import com.kingboy.axon.utils.MapperUtils;
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
public class Ticket {

    @AggregateIdentifier
    private String ticketId;

    private String name;

    private String lockUser;

    private String owner;


    @CommandHandler
    public Ticket(TicketCreateCommand command) {
        apply(MapperUtils.mapperBean(command, TicketCreatedEvent.class));
    }

    @EventSourcingHandler
    public void on(TicketCreatedEvent event) {
        this.ticketId = event.getTicketId();
        this.name = event.getName();
    }

    @CommandHandler
    public void handle(TicketLockCommand command) {
        if (this.owner != null) {
            apply(new TicketLockedFailEvent(command.getOrderId(), "the ticket has been ordered by " + this.owner));
            return;
        }
        if (this.lockUser != null && this.lockUser == command.getCustomerId()) {
            log.warn("duplicate lock ticket {} for {}", command.getTicketId(), command.getCustomerId());
            return;
        }
        if (this.lockUser == null) {
            apply(new TicketLockedEvent(command.getTicketId(), command.getCustomerId(), command.getOrderId()));
            return;
        }
        apply(new TicketLockedFailEvent(command.getOrderId(),
                "ticket locked failed, owner " + this.owner + " lockUser " + this.lockUser));

    }

    @EventSourcingHandler
    public void on(TicketLockedEvent event) {
        this.lockUser = event.getCustomerId();
        log.info("lock ticket {} for {}", event.getTicketId(), event.getCustomerId());
    }

    @CommandHandler
    public void handle(TicketUnlockCommand command) {
        if (this.lockUser == null) {
            log.warn("ticket {} was not locked", command.getTicketId());
        } else if (this.lockUser != command.getCustomerId()) {
            log.warn("ticket was not locked by {}", command.getCustomerId());
        } else {
            apply(new TicketUnlockedEvent(command.getTicketId()));
        }
    }

    @EventSourcingHandler
    public void on(TicketUnlockedEvent event) {
        this.lockUser = null;
    }

    @CommandHandler
    public void handle(TicketMoveCommand command) {
        if (this.owner != null) {
            log.warn("ticket not locked");
        } else if (this.lockUser != command.getCustomerId()) {
            log.error("ticket not locked by {}", command.getCustomerId());
        } else {
            apply(new TicketMovedEvent(command.getTicketId(), command.getCustomerId(), command.getOrderId()));
        }
    }

    @EventSourcingHandler
    public void on(TicketMovedEvent event) {
        this.lockUser = null;
        this.owner = event.getCustomerId();
    }

}
