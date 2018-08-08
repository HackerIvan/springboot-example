package com.kingboy.service;

import com.kingboy.dataobject.UserDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/12/8 上午1:03
 * @desc 消息生产者，发送消息到消息队列.
 */
@Service
public class ProviderService {

    @Resource
    AmqpTemplate amqpTemplate;


    public void send() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("123456");
        userDTO.setAddress("Beijing chaoyang");
        userDTO.setUsername("kingboy");
        System.out.println("发送消息" + userDTO.toString());
        amqpTemplate.convertAndSend("demo", userDTO);
    }



}
