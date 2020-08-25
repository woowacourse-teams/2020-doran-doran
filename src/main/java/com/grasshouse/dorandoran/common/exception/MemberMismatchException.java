package com.grasshouse.dorandoran.common.exception;

public class MemberMismatchException extends ExpectedException {

    public static final String POST_AUTHOR_MISMATCH_MESSAGE = "회원님의 글이 아닙니다.";
    public static final String COMMENT_AUTHOR_MISMATCH_MESSAGE = "회원님의 댓글이 아닙니다.";
    public static final String POST_LIKER_MISMATCH_MESSAGE = "회원님이 좋아요 한 글이 아닙니다.";
    public static final String COMMENT_LIKER_MISMATCH_MESSAGE = "회원님이 좋아요 한 댓글이 아닙니다.";

    public MemberMismatchException(String message) {
        super(message);
    }
}