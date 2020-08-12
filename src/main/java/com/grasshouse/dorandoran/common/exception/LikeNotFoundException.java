package com.grasshouse.dorandoran.common.exception;

public class LikeNotFoundException extends ExpectedException {

    public LikeNotFoundException(String item) {
        super("존재하지 않는 " + item + "의 좋아요 입니다.");
    }
}
