package com.kingboy.common.config.autentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingboy.common.config.property.ReturnType;
import com.kingboy.common.config.property.SecurityProperty;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/11/30 下午7:05
 * @desc 认证成功处理.
 */
@CommonsLog
@Component
/**
 * simpleurl不会返回到原先的请求
 */
public class KingSuucessfuleAuthentication extends SavedRequestAwareAuthenticationSuccessHandler {

    @Resource
    ObjectMapper objectMapper;

    @Resource
    SecurityProperty securityProperty;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        if (securityProperty.getReturnType() == ReturnType.JSON) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }


    }
}
