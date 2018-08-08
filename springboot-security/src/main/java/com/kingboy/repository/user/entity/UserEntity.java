package com.kingboy.repository.user.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/11/30 上午1:11
 * @desc 用户实体.
 */
@Entity
@Table(name = "user")
public class UserEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String address;

    private String authorities;

    private LocalDateTime lastLoginTime;

    private LocalDateTime expiredTime;

    private Boolean locked;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(this.authorities);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return expiredTime.isBefore(LocalDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return lastLoginTime.plusDays(100).isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        return this.status == Status.ENABLE;
    }


    public String getAddress() {
        return address;
    }

    public UserEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserEntity setAuthorities(String authorities) {
        this.authorities = authorities;
        return this;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public UserEntity setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
        return this;
    }

    public Boolean getLocked() {
        return locked;
    }

    public UserEntity setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public UserEntity setStatus(Status status) {
        this.status = status;
        return this;
    }


    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public UserEntity setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }
}
