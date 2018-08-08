package com.kingboy.common.tools.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/12/1 上午2:40
 * @desc .
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String message) {
        super(message);
    }
}
