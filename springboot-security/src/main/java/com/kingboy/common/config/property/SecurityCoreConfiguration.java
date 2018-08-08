package com.kingboy.common.config.property;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/11/30 上午3:57
 * @desc .
 */
@Configuration
@EnableConfigurationProperties(SecurityProperty.class)
public class SecurityCoreConfiguration {
}
