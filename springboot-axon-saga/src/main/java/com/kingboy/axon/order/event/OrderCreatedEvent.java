package com.kingboy.axon.order.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 17:54
 * Desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private String orderId;

    private String customerId;

    private String title;

    private String ticketId;

    private Double amount;

    private LocalDateTime createDate;

}
