package com.grasshouse.dorandoran.common.exception;

public class CommentAuthorMisMatchException extends ExpectedException {

    private static final String MESSAGE = "자신의 댓글이 아닙니다.";

    public CommentAuthorMisMatchException() {
        super(MESSAGE);
    }
}
