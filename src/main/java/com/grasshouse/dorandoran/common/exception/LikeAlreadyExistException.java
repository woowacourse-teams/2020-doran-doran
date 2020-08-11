package com.grasshouse.dorandoran.common.exception;

public class LikeAlreadyExistException extends ExpectedException {

    private static final String MESSAGE = "이미 좋아요를 눌렀습니다.";

    public LikeAlreadyExistException() {
        super(MESSAGE);
    }
}
