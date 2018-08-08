package com.kingboy.common.config.filter;

import com.kingboy.common.tools.exception.ValidateCodeException;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/12/1 上午2:31
 * @desc 验证码过滤器.
 */
@CommonsLog
public class ImageCodeFilter extends OncePerRequestFilter{

    public ImageCodeFilter(AuthenticationFailureHandler handler) {
        this.authenticationFailureHandler = handler;
    }

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equalsIgnoreCase(request.getMethod(), "post")
                && StringUtils.equals("/user/login", request.getRequestURI())) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(ServletRequestAttributes requestAttributes) throws ServletRequestBindingException {
        String sessionCode = (String) sessionStrategy.getAttribute(requestAttributes, "imageCode");
        LocalDateTime expireTime = (LocalDateTime) sessionStrategy.getAttribute(requestAttributes, "imageCodeTime");
        String requestCode = ServletRequestUtils.getStringParameter(requestAttributes.getRequest(), "imageCode");

        if (StringUtils.isBlank(requestCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        if (StringUtils.isBlank(requestCode) || expireTime == null) {
            throw new ValidateCodeException("服务端验证码信息错误");
        }

        if (expireTime.isBefore(LocalDateTime.now())) {
            sessionStrategy.removeAttribute(requestAttributes, "imageCode");
            sessionStrategy.removeAttribute(requestAttributes, "imageCodeTime");
            throw new ValidateCodeException("验证码已失效");
        }

        if (!StringUtils.equalsIgnoreCase(sessionCode, requestCode)) {
            throw new ValidateCodeException("验证码错误");
        }

        sessionStrategy.removeAttribute(requestAttributes, "imageCode");
        sessionStrategy.removeAttribute(requestAttributes, "imageCodeTime");
    }
}
