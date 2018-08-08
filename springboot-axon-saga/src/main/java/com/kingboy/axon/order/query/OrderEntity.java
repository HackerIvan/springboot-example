package com.kingboy.axon.order.query;

import com.kingboy.axon.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "tb_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    private String orderId;

    private String customerId;

    private String title;

    private String ticketId;

    private Double amount;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    private String reason;

    private LocalDateTime createDate;

}