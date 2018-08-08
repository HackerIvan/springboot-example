package com.kingboy.axon.customer.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 16:00
 * Desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerWithdrawCommand {

    @TargetAggregateIdentifier
    private String customerId;

    private Double amount;

}
