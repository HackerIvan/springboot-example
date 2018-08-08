package com.kingboy.axon.customer.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/31 02:14
 * Desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayCommand {

    @TargetAggregateIdentifier
    private String customerId;

    private String orderId;

    private Double amount;

}
