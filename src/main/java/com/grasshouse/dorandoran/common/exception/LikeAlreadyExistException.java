package com.grasshouse.dorandoran.common.exception;

public class LikeAlreadyExistException extends ExpectedException {

    private static final String MESSAGE = "존재하는 좋아요입니다.";

    public LikeAlreadyExistException() {
        super(MESSAGE);
    }
}
