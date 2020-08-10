package com.grasshouse.dorandoran.fixture;

import static com.grasshouse.dorandoran.fixture.PostFixture.PERSIST_POST;

import com.grasshouse.dorandoran.post.domain.PostLike;

public class PostLikeFixture {

    public static final PostLike PERSIST_POST_LIKE = PostLike.builder()
        .id(7L)
        .memberId(5L)
        .post(PERSIST_POST)
        .build();
}
