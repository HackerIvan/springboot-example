package com.kingboy.axon.order.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/31 01:56
 * Desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFailedEvent {

    private String orderId;

    private String reason;
}
