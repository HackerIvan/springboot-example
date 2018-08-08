package com.kingboy.axon.customer.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 16:01
 * Desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerWithdrawedEvent {

    private String customerId;

    private Double amount;

}