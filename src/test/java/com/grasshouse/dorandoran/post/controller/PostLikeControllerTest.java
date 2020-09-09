package com.grasshouse.dorandoran.post.controller;

import static com.grasshouse.dorandoran.fixture.MemberFixture.PERSIST_MEMBER;
import static com.grasshouse.dorandoran.fixture.PostLikeFixture.PERSIST_POST_LIKE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grasshouse.dorandoran.common.CommonControllerTest;
import com.grasshouse.dorandoran.config.jwt.JwtTokenProvider;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.post.dto.PostLikeCreateRequest;
import com.grasshouse.dorandoran.post.service.PostLikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

public class PostLikeControllerTest extends CommonControllerTest {

    @MockBean
    private PostLikeService postLikeService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private MemberRepository memberRepository;

    @DisplayName("로그인된 사용자가 게시물에 좋아요를 추가한다.")
    @Test
    void createPostLikeWithLoginUser() throws Exception {
        PostLikeCreateRequest postLikeCreateRequest = PostLikeCreateRequest.builder()
            .postId(10L)
            .build();

        String request = objectMapper.writeValueAsString(postLikeCreateRequest);
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getSubject(anyString())).thenReturn("id");
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);
        when(postLikeService.createPostLike(any(), any())).thenReturn(10L);

        this.mockMvc.perform(post("/posts/likes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(status().isCreated())
            .andDo(print());

        verify(postLikeService).createPostLike(any(), any());
    }

    @DisplayName("[예외] 로그인하지 않은 사용자가 게시물에 좋아요를 추가한다.")
    @Test
    void createPostLikeWithoutLogin() throws Exception {
        PostLikeCreateRequest postLikeCreateRequest = PostLikeCreateRequest.builder()
            .postId(10L)
            .build();

        String request = objectMapper.writeValueAsString(postLikeCreateRequest);

        this.mockMvc.perform(post("/posts/likes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }

    @DisplayName("로그인된 사용자가 게시물의 좋아요를 취소(삭제)한다.")
    @Test
    void deletePostLikeWithLoginUser() throws Exception {
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getSubject(anyString())).thenReturn("id");
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);
        doNothing().when(postLikeService).deletePostLike(anyLong(), any());

        this.mockMvc.perform(delete("/posts/likes/" + PERSIST_POST_LIKE.getId()))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @DisplayName("[예외] 로그인 하지 않은 사용자가 게시물의 좋아요를 취소(삭제)한다.")
    @Test
    void deletePostLikeWithoutLogin() throws Exception {
        doNothing().when(postLikeService).deletePostLike(anyLong(), any());

        this.mockMvc.perform(delete("/posts/likes/" + PERSIST_POST_LIKE.getId()))
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }
}
