package com.grasshouse.dorandoran.fixture;

import com.grasshouse.dorandoran.member.domain.Member;

public class MemberFixture {

    public static final Member PERSIST_MEMBER = Member.builder()
        .id(5L)
        .nickname("오구")
        .build();
}
