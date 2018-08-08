package com.kingboy.axon.order.query;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 13:29
 * Desc
 */
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    Optional<OrderEntity> findByOrOrderId(String orderId);
}
