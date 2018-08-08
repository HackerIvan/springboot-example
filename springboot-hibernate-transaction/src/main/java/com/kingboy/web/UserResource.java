package com.kingboy.web;

import com.kingboy.domain.User;
import com.kingboy.repository.UserRepository;
import com.kingboy.service.UserServiceWithAnnotation;
import com.kingboy.service.UserServiceWithCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * User kingboy - KingBoyWorld@163.com
 * Date 2018/7/27 16:17
 * Desc 用户资源接口
 */
@RestController
@RequestMapping(value = "/user")
public class UserResource {

    @Resource
    UserServiceWithAnnotation userServiceWithAnnotation;

    @Resource
    UserServiceWithCode userServiceWithCode;

    @Resource
    UserRepository userRepository;

    @PostMapping(value = "/annotation")
    public void createWithAnnotation(@RequestBody User user) {
        userServiceWithAnnotation.create(user);
    }

    @PostMapping(value = "/code")
    public void createWithCode(@RequestBody User user) {
        userServiceWithCode.create(user);
    }

    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
