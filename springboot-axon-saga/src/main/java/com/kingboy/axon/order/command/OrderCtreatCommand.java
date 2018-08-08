package com.kingboy.axon.order.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 17:54
 * Desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCtreatCommand {

    @TargetAggregateIdentifier
    private String orderId;

    private String customerId;

    private String title;

    private String ticketId;

    private Double amount;

}
