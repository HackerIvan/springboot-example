package com.kingboy.common.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/12/8 上午1:00
 * @desc rabbitMQ消息通道配置.
 */
@Configuration
public class RabbitMQConfiguration {

    @Bean
    public Queue demoQueue() {
        return new Queue("demo");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
