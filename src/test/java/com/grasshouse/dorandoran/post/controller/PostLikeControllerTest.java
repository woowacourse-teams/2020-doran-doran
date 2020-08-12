package com.grasshouse.dorandoran.post.controller;

import static com.grasshouse.dorandoran.fixture.PostFixture.PERSIST_POST;
import static com.grasshouse.dorandoran.fixture.PostLikeFixture.PERSIST_POST_LIKE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grasshouse.dorandoran.common.CommonControllerTest;
import com.grasshouse.dorandoran.post.service.PostLikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class PostLikeControllerTest extends CommonControllerTest {

    @MockBean
    private PostLikeService postLikeService;

    @DisplayName("게시물에 좋아요를 추가한다.")
    @Test
    void createPostLike() throws Exception {
        when(postLikeService.createPostLike(any(), any())).thenReturn(PERSIST_POST.getId());

        this.mockMvc.perform(post("/posts/likes")
            .queryParam("postId", "10")
            .queryParam("memberId", "5"))
            .andExpect(status().isCreated())
            .andDo(print());

        verify(postLikeService).createPostLike(any(), any());
    }

    @DisplayName("게시물의 좋아요를 취소(삭제)한다.")
    @Test
    void deletePostLike() throws Exception {
        doNothing().when(postLikeService).deletePostLike(PERSIST_POST_LIKE.getId());
        this.mockMvc.perform(delete("/posts/likes/" + PERSIST_POST_LIKE.getId()))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

}