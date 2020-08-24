package com.grasshouse.dorandoran.common.exception;

public class CommentLikerMismatchException extends ExpectedException {

    private static final String MESSAGE = "댓글을 좋아요한 사람이 아닙니다.";

    public CommentLikerMismatchException() {
        super(MESSAGE);
    }
}
