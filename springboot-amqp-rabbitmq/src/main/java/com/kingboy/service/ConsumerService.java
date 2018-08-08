package com.kingboy.service;

import com.kingboy.dataobject.UserDTO;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/12/8 上午1:06
 * @desc 监听队列，并消费消息.
 */
@Service
public class ConsumerService {

    @RabbitHandler
    @RabbitListener(queues = "demo")
    public void receiver(UserDTO userDTO) {
        System.out.println("接收到消息：" + userDTO.toString());
    }

}
