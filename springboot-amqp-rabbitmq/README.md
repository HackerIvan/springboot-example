### 安装教程

[CentOS6.x与7.x安装rabbitMQ](readme/install.md)

### 简单版教程

依赖
```groovy
compile('org.springframework.boot:spring-boot-starter-amqp')
```

配置
```java
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
```

发送方

```java
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

```

接收方
```java
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

```