package com.grasshouse.dorandoran.fixture;

import static com.grasshouse.dorandoran.fixture.CommentFixture.PERSIST_COMMENT;

import com.grasshouse.dorandoran.comment.domain.CommentLike;

public class CommentLikeFixture {

    public static final CommentLike PERSIST_COMMENT_LIKE = CommentLike.builder()
        .id(7L)
        .memberId(5L)
        .comment(PERSIST_COMMENT)
        .build();
}
