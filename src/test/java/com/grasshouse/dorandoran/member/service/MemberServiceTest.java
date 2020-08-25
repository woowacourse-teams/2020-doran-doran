package com.grasshouse.dorandoran.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.member.service.dto.MemberUpdateRequest;
import com.grasshouse.dorandoran.member.service.dto.MemberUpdateResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
            .nickname("nickname")
            .oAuthId("oauthId")
            .build();

        memberRepository.save(member);
    }

    @DisplayName("사용자의 닉네임을 업데이트한다.")
    @Test
    void update() {
        MemberUpdateRequest request = new MemberUpdateRequest("new nickname");

        MemberUpdateResponse response = memberService.update(member, request);

        assertThat(response.getNickname()).isEqualTo(request.getNickname());
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }
}