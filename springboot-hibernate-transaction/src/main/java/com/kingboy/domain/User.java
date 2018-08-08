package com.kingboy.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/27 15:43
 * Desc 用户实体
 */
@Data
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

}
