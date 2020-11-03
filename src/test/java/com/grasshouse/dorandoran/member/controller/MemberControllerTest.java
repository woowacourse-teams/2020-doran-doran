package com.grasshouse.dorandoran.member.controller;

import static com.grasshouse.dorandoran.fixture.MemberFixture.PERSIST_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grasshouse.dorandoran.common.CommonControllerTest;
import com.grasshouse.dorandoran.common.config.jwt.JwtTokenProvider;
import com.grasshouse.dorandoran.member.dto.MemberUpdateRequest;
import com.grasshouse.dorandoran.member.dto.MemberUpdateResponse;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
class MemberControllerTest extends CommonControllerTest {

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private MemberService memberService;

    @DisplayName("로그인된 사용자가 자신의 정보를 요청한다.")
    @Test
    void getProfileWithLoginUser() throws Exception {
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getSubject(anyString())).thenReturn("id");
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);

        this.mockMvc.perform(get("/me"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("[예외] 로그인 하지않은 사용자가 자신의 정보를 요청한다.")
    @Test
    void getProfileWithoutLogin() throws Exception {
        this.mockMvc.perform(get("/me"))
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }

    @DisplayName("로그인된 사용자가 자신의 정보를 수정한다.")
    @Test
    void updateMember() throws Exception {
        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest("new nickname");
        String request = objectMapper.writeValueAsString(memberUpdateRequest);

        MemberUpdateResponse response = new MemberUpdateResponse(PERSIST_MEMBER.getId(),
            "new nickname");

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getSubject(anyString())).thenReturn("id");
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);
        when(memberService.update(any(), any())).thenReturn(response);

        MvcResult mvcResult = this.mockMvc.perform(put("/me")
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
            .contains(PERSIST_MEMBER.getId().toString());
        assertThat(mvcResult.getResponse().getContentAsString()).contains("new nickname");
        verify(memberService).update(any(), any());
    }

    @DisplayName("로그인된 사용자가 탈퇴한다.")
    @Test
    void deleteMember() throws Exception {
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getSubject(anyString())).thenReturn("id");
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);
        doNothing().when(memberService).delete(any());

        this.mockMvc.perform(delete("/me"))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andReturn();

        verify(memberService).delete(any());
    }
}