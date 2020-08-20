package com.grasshouse.dorandoran.comment.controller;

import static com.grasshouse.dorandoran.fixture.CommentFixture.PERSIST_COMMENT;
import static com.grasshouse.dorandoran.fixture.LocationFixture.GANGNAM_STATION;
import static com.grasshouse.dorandoran.fixture.MemberFixture.PERSIST_MEMBER;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grasshouse.dorandoran.comment.service.CommentService;
import com.grasshouse.dorandoran.comment.service.dto.CommentCreateRequest;
import com.grasshouse.dorandoran.common.CommonControllerTest;
import com.grasshouse.dorandoran.common.exception.CommentOwnerMisMatchException;
import com.grasshouse.dorandoran.config.jwt.JwtTokenProvider;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;

class CommentControllerTest extends CommonControllerTest {

    @MockBean
    private CommentService commentService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private MemberRepository memberRepository;

    @DisplayName("로그인한 사용자가 댓글을 작성한다.")
    @Test
    void createCommentWithLoginUser() throws Exception {
        CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder()
            .memberId(PERSIST_MEMBER.getId())
            .postId(1L)
            .content("댓글입니다.")
            .location(GANGNAM_STATION)
            .build();

        String request = objectMapper.writeValueAsString(commentCreateRequest);
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);
        when(commentService.createComment(any(), any())).thenReturn(1L);

        this.mockMvc.perform(post("/comments")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(status().isCreated())
            .andDo(print());

        verify(commentService).createComment(any(), any());
    }

    @DisplayName("[예외] 로그인하지 않은 사용자가 댓글을 작성한다.")
    @Test
    void createCommentWithoutLogin() throws Exception {
        CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder()
            .memberId(PERSIST_MEMBER.getId())
            .postId(1L)
            .content("댓글입니다.")
            .location(GANGNAM_STATION)
            .build();

        String request = objectMapper.writeValueAsString(commentCreateRequest);

        this.mockMvc.perform(post("/comments")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }

    @DisplayName("로그인한 사용자가 자신의 댓글을 삭제한다.")
    @Test
    void deleteCommentWithLoginUser() throws Exception {
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);
        doNothing().when(commentService)
            .deleteComment(anyLong(), any());
        this.mockMvc.perform(
            delete("/comments/" + PERSIST_COMMENT.getId()))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @DisplayName("[예외] 로그인하지 않은 사용자가 댓글을 삭제한다.")
    @Test
    void deleteCommentWithoutLogin() throws Exception {
        doNothing().when(commentService)
            .deleteComment(anyLong(), any());
        this.mockMvc.perform(
            delete("/comments/" + PERSIST_COMMENT.getId()))
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }

    @DisplayName("[예외] 다른 사용자의 댓글을 삭제한다.")
    @Test
    void deleteAnotherAuthorsComment() throws Exception {
        Member anotherMember = Member.builder()
            .id(7L)
            .nickname("엘리")
            .build();

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(memberRepository.findByoAuthId(anyString())).thenReturn(anotherMember);
        doThrow(CommentOwnerMisMatchException.class).when(commentService)
            .deleteComment(anyLong(), any());
        this.mockMvc.perform(
            delete("/comments/" + PERSIST_COMMENT.getId()))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("[예외] CommentCreateRequest DTO의 내용이 120자를 넘는다.")
    @Test
    void commentCreateDtoTooLong() throws Exception {
        CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder()
            .memberId(PERSIST_MEMBER.getId())
            .postId(1L)
            .content("댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다"
                + "댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다"
                + "댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다꽝")
            .location(GANGNAM_STATION)
            .build();

        String request = objectMapper.writeValueAsString(commentCreateRequest);

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);

        this.mockMvc.perform(post("/comments")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(result -> assertTrue(
                result.getResolvedException() instanceof MethodArgumentNotValidException))
            .andDo(print());
    }

}
