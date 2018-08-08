package com.kingboy.axon.ticket.query;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/30 13:28
 * Desc
 */
public interface TicketRepository extends JpaRepository<TicketEntity, String> {
    Optional<TicketEntity> findByTicketId(String ticketId);
}
