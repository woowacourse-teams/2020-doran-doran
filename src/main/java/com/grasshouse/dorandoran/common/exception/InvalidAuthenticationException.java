package com.grasshouse.dorandoran.common.exception;

public class InvalidAuthenticationException extends RuntimeException {

    private static final String INVALID_TOKEN_MESSAGE = "올바르지 않은 토큰 입니다.";

    public InvalidAuthenticationException() {
        super(INVALID_TOKEN_MESSAGE);
    }
}
