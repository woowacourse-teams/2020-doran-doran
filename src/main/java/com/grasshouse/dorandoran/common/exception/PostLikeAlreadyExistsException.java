package com.grasshouse.dorandoran.common.exception;

public class PostLikeAlreadyExistsException extends ExpectedException {

    private static final String MESSAGE = "게시글의 좋아요가 이미 존재합니다.";

    public PostLikeAlreadyExistsException() {
        super(MESSAGE);
    }
}
