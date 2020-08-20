package com.grasshouse.dorandoran.common.exception;

public class PostLikerMisMatchException extends ExpectedException {

    private static final String MESSAGE = "글을 좋아요한 사람이 아닙니다.";

    public PostLikerMisMatchException() {
        super(MESSAGE);
    }
}
