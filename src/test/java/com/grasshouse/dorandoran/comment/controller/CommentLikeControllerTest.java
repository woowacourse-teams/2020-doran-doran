package com.grasshouse.dorandoran.comment.controller;

import static com.grasshouse.dorandoran.fixture.CommentLikeFixture.PERSIST_COMMENT_LIKE;
import static com.grasshouse.dorandoran.fixture.MemberFixture.PERSIST_MEMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grasshouse.dorandoran.comment.service.CommentLikeService;
import com.grasshouse.dorandoran.comment.dto.CommentLikeCreateRequest;
import com.grasshouse.dorandoran.common.CommonControllerTest;
import com.grasshouse.dorandoran.config.jwt.JwtTokenProvider;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

public class CommentLikeControllerTest extends CommonControllerTest {

    @MockBean
    private CommentLikeService commentLikeService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private MemberRepository memberRepository;

    @DisplayName("로그인된 사용자가 댓글에 좋아요를 추가한다.")
    @Test
    void createCommentLikeWithLoginUser() throws Exception {
        CommentLikeCreateRequest commentLikeCreateRequest = CommentLikeCreateRequest.builder()
            .commentId(10L)
            .build();

        String request = objectMapper.writeValueAsString(commentLikeCreateRequest);
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getSubject(anyString())).thenReturn("id");
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);
        when(commentLikeService.createCommentLike(any(), any())).thenReturn(1L);

        this.mockMvc.perform(post("/comments/likes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(status().isCreated())
            .andDo(print());

        verify(commentLikeService).createCommentLike(any(), any());
    }

    @DisplayName("[예외] 로그인 하지 않은 사용자가 댓글에 좋아요를 추가한다.")
    @Test
    void createCommentLikeWithoutLogin() throws Exception {
        CommentLikeCreateRequest commentLikeCreateRequest = CommentLikeCreateRequest.builder()
            .commentId(10L)
            .build();

        String request = objectMapper.writeValueAsString(commentLikeCreateRequest);

        this.mockMvc.perform(post("/comments/likes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }

    @DisplayName("로그인된 사용자가 댓글 좋아요를 취소(삭제)한다.")
    @Test
    void deleteCommentLikeWithLoginUser() throws Exception {
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getSubject(anyString())).thenReturn("id");
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);
        doNothing().when(commentLikeService)
            .deleteCommentLike(any(), any());

        this.mockMvc
            .perform(delete("/comments/likes/" + PERSIST_COMMENT_LIKE.getId()))
            .andExpect(status().isNoContent())
            .andDo(print());

        verify(commentLikeService).deleteCommentLike(any(), any());
    }

    @DisplayName("[예외] 로그인 하지 않은 사용자가 댓글 좋아요를 취소(삭제)한다.")
    @Test
    void deleteCommentLikeWithoutLogin() throws Exception {
        this.mockMvc
            .perform(delete("/comments/likes/" + PERSIST_COMMENT_LIKE.getId()))
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }
}
