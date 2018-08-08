package com.kingboy.axon.customer.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 14:48
 * Desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateCommand {

    @TargetAggregateIdentifier
    private String customerId;

    private String username;

    private String password;

}
