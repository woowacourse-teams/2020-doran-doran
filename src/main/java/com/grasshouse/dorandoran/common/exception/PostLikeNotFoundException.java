package com.grasshouse.dorandoran.common.exception;

public class PostLikeNotFoundException extends ExpectedException {

    private static final String MESSAGE = "존재하지 않는 게시글 좋아요 입니다.";

    public PostLikeNotFoundException() {
        super(MESSAGE);
    }
}
