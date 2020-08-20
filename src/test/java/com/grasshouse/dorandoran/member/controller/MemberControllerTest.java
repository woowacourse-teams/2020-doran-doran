package com.grasshouse.dorandoran.member.controller;

import static com.grasshouse.dorandoran.fixture.MemberFixture.PERSIST_MEMBER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grasshouse.dorandoran.common.CommonControllerTest;
import com.grasshouse.dorandoran.config.jwt.JwtTokenProvider;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class MemberControllerTest extends CommonControllerTest {

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private MemberRepository memberRepository;

    @DisplayName("로그인된 사용자가 자신의 정보를 요청한다.")
    @Test
    void getProfileWithLoginUser() throws Exception {
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getSubject(anyString())).thenReturn("id");
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);

        this.mockMvc.perform(get("/my"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("[예외] 로그인 하지않은 사용자가 자신의 정보를 요청한다.")
    @Test
    void getProfileWithoutLogin() throws Exception {
        this.mockMvc.perform(get("/my"))
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }
}