package com.kingboy.axon.ticket.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tb_ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {

    @Id
    private String ticketId;

    private String name;

    private String lockUser;

    private String owner;

}
