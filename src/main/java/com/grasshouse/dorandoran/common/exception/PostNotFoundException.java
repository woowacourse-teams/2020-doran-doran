package com.grasshouse.dorandoran.common.exception;

public class PostNotFoundException extends DoranDoranException {

    private static final String MESSAGE = "존재하지 않는 게시글입니다.";

    public PostNotFoundException() {
        super(MESSAGE);
    }
}
