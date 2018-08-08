package com.kingboy.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingboy.common.config.property.SecurityProperty;
import com.kingboy.common.utils.result.ApiResult;
import com.kingboy.service.user.response.UserDTO;
import com.kingboy.service.user.UserService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/11/30 上午1:10
 * @desc 用户访问层.
 */
@RestController
@RequestMapping("/user")
@CommonsLog
public class UserController {

    @Resource
    UserService userService;

    @Resource
    ObjectMapper objectMapper;


    @GetMapping("/me")
    public UserDetails getCurrentUser(Authentication authentication, @AuthenticationPrincipal UserDetails user) throws JsonProcessingException {
        log.info(objectMapper.writeValueAsString(authentication));
        log.info(objectMapper.writeValueAsString(SecurityContextHolder.getContext().getAuthentication()));
        return user;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public ApiResult saveUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return ApiResult.success("success");
    }

    RequestCache requestCache = new HttpSessionRequestCache();

    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Resource(type = SecurityProperty.class)
    SecurityProperty securityProperty;

    @RequestMapping("/auth")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ApiResult auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest requestCacheRequest = requestCache.getRequest(request, response);
        if (requestCacheRequest != null) {
            Map<String, String[]> parameterMap = requestCacheRequest.getParameterMap();

            List<String> teminals = parameterMap.size() > 0 ? Arrays.asList(parameterMap.get("terminal")) : Collections.emptyList();
            if (teminals.contains("app")) {
                redirectStrategy.sendRedirect(request, response, securityProperty.getAppPage());
            }
            if (teminals.contains("brower")) {
                redirectStrategy.sendRedirect(request, response, securityProperty.getBrowerPage());
            }
        }
        return ApiResult.error("-1", "请引导用户到登录页");
    }

}
