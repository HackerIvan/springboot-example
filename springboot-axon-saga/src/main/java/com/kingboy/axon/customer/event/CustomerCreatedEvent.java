package com.kingboy.axon.customer.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 14:59
 * Desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreatedEvent {

    private String customerId;

    private String username;

    private String password;

}
