package com.kingboy.axon.customer.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/31 02:16
 * Desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaidEvent {

    private String orderId;

    private String customerId;

    private Double amount;
}
