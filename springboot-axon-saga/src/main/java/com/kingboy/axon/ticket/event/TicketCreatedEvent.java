package com.kingboy.axon.ticket.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/31 01:10
 * Desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketCreatedEvent {

    private String ticketId;

    private String name;

}
