package com.grasshouse.dorandoran.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidAuthenticationException extends RuntimeException {

    private static final String INVALID_TOKEN_MESSAGE = "올바르지 않은 토큰 입니다.";

    public InvalidAuthenticationException() {
        super(INVALID_TOKEN_MESSAGE);
    }
}
