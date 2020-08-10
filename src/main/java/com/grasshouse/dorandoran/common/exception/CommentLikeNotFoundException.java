package com.grasshouse.dorandoran.common.exception;

public class CommentLikeNotFoundException extends ExpectedException {

    private static final String MESSAGE = "좋아요를 누른 내역이 없습니다.";

    public CommentLikeNotFoundException() {
        super(MESSAGE);
    }
}