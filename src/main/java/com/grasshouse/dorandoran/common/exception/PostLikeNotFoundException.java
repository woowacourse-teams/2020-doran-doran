package com.grasshouse.dorandoran.common.exception;

public class PostLikeNotFoundException extends ExpectedException {

    private static final String MESSAGE = "게시글 좋아요를 누른 내역이 없습니다.";

    public PostLikeNotFoundException() {
        super(MESSAGE);
    }
}
