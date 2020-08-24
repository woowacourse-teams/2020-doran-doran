package com.grasshouse.dorandoran.common.exception;

public class PostAuthorMismatchException extends ExpectedException {

    private static final String MESSAGE = "자신의 글이 아닙니다.";

    public PostAuthorMismatchException() {
        super(MESSAGE);
    }
}
