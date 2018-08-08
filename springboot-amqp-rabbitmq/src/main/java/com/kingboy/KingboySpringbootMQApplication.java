package com.kingboy;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class KingboySpringbootMQApplication {

    public static void main(String[] args) {
        SpringApplication.run(KingboySpringbootMQApplication.class, args);
    }
}
