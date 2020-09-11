package com.grasshouse.dorandoran.member.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.grasshouse.dorandoran.member.domain.Member;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("가능한 닉네임들로 Member를 생성한다.")
    @Test
    void possibleNicknameTest() {
        Member member = Member.builder().nickname("일이삼사오육칠팔구십일이삼사오").oAuthId("oauthId").build();
        Member member2 = Member.builder().nickname("ottffssentottff").oAuthId("oauthId2").build();
        Member member3 = Member.builder().nickname("123456789012345").oAuthId("oauthId3").build();

        assertAll(
            () -> assertThat(memberRepository.save(member)).isNotNull(),
            () -> assertThat(memberRepository.save(member2)).isNotNull(),
            () -> assertThat(memberRepository.save(member3)).isNotNull()
        );
    }

    @DisplayName("[예외] 가능하지 않은 닉네임들로 Member를 생성한다.")
    @Test
    void impossibleNicknameTest() {
        Member member = Member.builder().nickname("일이삼사오육칠팔구십일이삼사오육").build();
        Member member2 = Member.builder().nickname("ottffssentottffs").build();
        Member member3 = Member.builder().nickname("1234567890123456").build();
        assertThatThrownBy(() -> {
            memberRepository.save(member);
            memberRepository.save(member2);
            memberRepository.save(member3);
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

}
