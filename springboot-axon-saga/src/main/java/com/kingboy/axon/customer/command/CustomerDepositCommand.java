package com.kingboy.axon.customer.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.Min;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 16:00
 * Desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDepositCommand {

    @TargetAggregateIdentifier
    private String customerId;

    @Min(value = 1, message = "存款金额至少为1")
    private Double amount;

}
