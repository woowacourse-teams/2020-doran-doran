package com.grasshouse.dorandoran.common.exception;

public class CommentLikeAlreadyExistsException extends ExpectedException {

    private static final String MESSAGE = "댓글의 좋아요가 이미 존재합니다.";

    public CommentLikeAlreadyExistsException() {
        super(MESSAGE);
    }
}
