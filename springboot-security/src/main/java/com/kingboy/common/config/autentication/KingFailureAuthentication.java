package com.kingboy.common.config.autentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingboy.common.config.property.ReturnType;
import com.kingboy.common.config.property.SecurityProperty;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/11/30 下午7:17
 * @desc 登录失败处理.
 */
@Component
@CommonsLog
public class KingFailureAuthentication extends SimpleUrlAuthenticationFailureHandler {

    @Resource
    ObjectMapper objectMapper;

    @Resource
    SecurityProperty securityProperty;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.error("登录失败");
        if (securityProperty.getReturnType() == ReturnType.JSON) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(objectMapper.writeValueAsString(exception.getMessage()));
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
