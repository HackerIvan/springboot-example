package com.kingboy.axon.order.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/31 01:55
 * Desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFailCommand {

    @TargetAggregateIdentifier
    private String orderId;

    private String reason;

}
