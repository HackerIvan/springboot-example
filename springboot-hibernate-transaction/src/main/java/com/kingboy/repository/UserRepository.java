package com.kingboy.repository;

import com.kingboy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/27 15:49
 * Desc 用户仓储
 */
public interface UserRepository extends JpaRepository<User, Integer> {

}
