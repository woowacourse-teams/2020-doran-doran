package com.grasshouse.dorandoran.common.exception;

public class CommentLikeNotFoundException extends ExpectedException {

    private static final String MESSAGE = "댓글의 좋아요가 존재하지 않습니다.";

    public CommentLikeNotFoundException() {
        super(MESSAGE);
    }
}
