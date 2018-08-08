package com.kingboy.axon.ticket.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/31 01:34
 * Desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketLockedFailEvent {

    private String orderId;

    private String reason;

}
