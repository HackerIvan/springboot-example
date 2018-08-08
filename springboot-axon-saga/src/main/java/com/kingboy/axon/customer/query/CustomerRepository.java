package com.kingboy.axon.customer.query;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 13:30
 * Desc
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByCustomerId(String id);
}
