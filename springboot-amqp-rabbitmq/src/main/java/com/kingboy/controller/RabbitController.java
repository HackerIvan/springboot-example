package com.kingboy.controller;

import com.kingboy.service.ProviderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/12/8 上午1:11
 * @desc .
 */
@RestController
public class RabbitController {

    @Resource
    ProviderService providerService;

    @GetMapping(value = "/send")
    public void send() {
        providerService.send();
    }
}
