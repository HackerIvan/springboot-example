package com.kingboy.service;

import com.kingboy.domain.User;
import com.kingboy.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/27 15:51
 * Desc 用户服务
 */
@Service
public class UserServiceWithAnnotation {

    @Resource
    UserRepository userRepository;

    @Transactional
    public void create(User user) {
        userRepository.save(user);
    }

}
