package com.grasshouse.dorandoran.fixture;

import static com.grasshouse.dorandoran.fixture.AddressFixture.ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.LOCATION;
import static com.grasshouse.dorandoran.fixture.MemberFixture.MEMBER;
import static com.grasshouse.dorandoran.fixture.MemberFixture.PERSIST_MEMBER;

import com.grasshouse.dorandoran.post.domain.Post;

public class PostFixture {

    public static final Post POST = Post.builder()
        .author(MEMBER)
        .content("내용")
        .location(LOCATION)
        .address(ADDRESS)
        .build();

    public static final Post PERSIST_POST = Post.builder()
        .id(10L)
        .author(PERSIST_MEMBER)
        .content("내용")
        .location(LOCATION)
        .address(ADDRESS)
        .build();
}
