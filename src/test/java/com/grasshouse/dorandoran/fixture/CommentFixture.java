package com.grasshouse.dorandoran.fixture;

import static com.grasshouse.dorandoran.fixture.MemberFixture.PERSIST_MEMBER;
import static com.grasshouse.dorandoran.fixture.PostFixture.PERSIST_POST;

import com.grasshouse.dorandoran.comment.domain.Comment;

public class CommentFixture {

    public static final Comment PERSIST_COMMENT = Comment.builder()
        .id(10L)
        .author(PERSIST_MEMBER)
        .content("댓글1")
        .post(PERSIST_POST)
        .distance(1.0)
        .build();
}
