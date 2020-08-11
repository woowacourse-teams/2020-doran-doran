package com.grasshouse.dorandoran.comment.controller;

import static com.grasshouse.dorandoran.fixture.CommentLikeFixture.PERSIST_COMMENT_LIKE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grasshouse.dorandoran.comment.service.CommentLikeService;
import com.grasshouse.dorandoran.common.CommonControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class CommentLikeControllerTest extends CommonControllerTest {

    @MockBean
    private CommentLikeService commentLikeService;

    @DisplayName("댓글에 좋아요를 추가한다.")
    @Test
    void likesComment() throws Exception {
        when(commentLikeService.createCommentLike(any(), any()))
            .thenReturn(PERSIST_COMMENT_LIKE.getId());

        this.mockMvc.perform(post("/comments/likes")
            .queryParam("commentId", "10")
            .queryParam("memberId", "5"))
            .andExpect(status().isCreated())
            .andDo(print());

        verify(commentLikeService).createCommentLike(any(), any());
    }

    @DisplayName("댓글 좋아요를 취소(삭제)한다.")
    @Test
    void cancelCommentLike() throws Exception {
        doNothing().when(commentLikeService)
            .deleteCommentLike(PERSIST_COMMENT_LIKE.getId());

        this.mockMvc
            .perform(delete("/comments/likes/" + PERSIST_COMMENT_LIKE.getId()))
            .andExpect(status().isNoContent())
            .andDo(print());

        verify(commentLikeService).deleteCommentLike(any());
    }
}
