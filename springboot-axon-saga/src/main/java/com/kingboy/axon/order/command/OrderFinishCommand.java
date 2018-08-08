package com.kingboy.axon.order.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by mavlarn on 2018/5/24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFinishCommand {

    @TargetAggregateIdentifier
    private String orderId;

}
