package com.grasshouse.dorandoran.common.exception;

public class PostLikeNotFoundException extends ExpectedException {

    private static final String MESSAGE = "게시글의 좋아요가 존재하지 않습니다.";

    public PostLikeNotFoundException() {
        super(MESSAGE);
    }
}
