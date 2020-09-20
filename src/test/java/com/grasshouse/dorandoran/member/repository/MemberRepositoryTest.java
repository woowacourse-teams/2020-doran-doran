package com.grasshouse.dorandoran.member.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.grasshouse.dorandoran.member.domain.Member;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("가능한 닉네임들로 Member를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"일이삼사오육칠팔구십일이삼사오", "ottffssentottff", "123456789012345"})
    void possibleNicknameTest(String nickname) {
        Member member = Member.builder().nickname(nickname).oAuthId("oauthId").build();
        assertThat(memberRepository.save(member)).isNotNull();
    }

    @DisplayName("[예외] 가능하지 않은 닉네임들로 Member를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"일이삼사오육칠팔구십일이삼사오육", "ottffssentottffs", "1234567890123456"})
    void impossibleNicknameTest(String nickname) {
        Member member = Member.builder().nickname(nickname).oAuthId("oauthId").build();
        assertThatThrownBy(() -> {
            memberRepository.save(member);
        }).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("닉네임은 15자를");
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

}
