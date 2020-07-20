package com.grasshouse.dorandoran.common.exception;

public class CommentNotFoundException extends ExpectedException {

    private static final String MESSAGE = "존재하지 않는 댓글입니다.";

    public CommentNotFoundException() {
        super(MESSAGE);
    }
}
