package com.kingboy.common.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/11/30 上午3:56
 * @desc 安全配置.
 */
@Data
@ConfigurationProperties(prefix = "kingboy.security")
public class SecurityProperty {

    private String browerPage = "/brower-login.html";

    private String appPage = "/app-login.html";

    private ReturnType returnType = ReturnType.JSON;

}
