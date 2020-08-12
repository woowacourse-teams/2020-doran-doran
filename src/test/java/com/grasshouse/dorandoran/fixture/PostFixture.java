package com.grasshouse.dorandoran.fixture;

import static com.grasshouse.dorandoran.fixture.AddressFixture.ADDRESS;
import static com.grasshouse.dorandoran.fixture.AddressFixture.AUTHOR_ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static com.grasshouse.dorandoran.fixture.MemberFixture.PERSIST_MEMBER;
import static com.grasshouse.dorandoran.fixture.PostLikeFixture.PERSIST_POST_LIKE;

import com.grasshouse.dorandoran.post.domain.Post;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PostFixture {

    public static final Post PERSIST_POST = Post.builder()
        .id(10L)
        .author(PERSIST_MEMBER)
        .authorAddress(AUTHOR_ADDRESS)
        .content("내용")
        .location(JAMSIL_STATION)
        .address(ADDRESS)
        .build();
}
