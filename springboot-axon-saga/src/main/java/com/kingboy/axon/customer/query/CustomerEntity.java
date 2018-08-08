package com.kingboy.axon.customer.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tb_customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

    @Id
    private String customerId;

    private String username;

    private String password;

    private Double amount;

}
