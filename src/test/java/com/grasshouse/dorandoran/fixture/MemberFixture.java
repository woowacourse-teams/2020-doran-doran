package com.grasshouse.dorandoran.fixture;

import com.grasshouse.dorandoran.member.domain.Member;

public class MemberFixture {

    public static final Member MEMBER = Member.builder()
        .id(1L)
        .nickname("오구")
        .build();

}
