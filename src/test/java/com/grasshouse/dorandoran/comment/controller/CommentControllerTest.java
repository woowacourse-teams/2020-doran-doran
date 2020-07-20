package com.grasshouse.dorandoran.comment.controller;

import static com.grasshouse.dorandoran.fixture.CommentFixture.PERSIST_COMMENT;
import static com.grasshouse.dorandoran.fixture.LocationFixture.GANGNAM_STATION;
import static com.grasshouse.dorandoran.fixture.MemberFixture.PERSIST_MEMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grasshouse.dorandoran.comment.service.CommentService;
import com.grasshouse.dorandoran.comment.service.dto.CommentCreateRequest;
import com.grasshouse.dorandoran.common.CommonControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

class CommentControllerTest extends CommonControllerTest {

    @MockBean
    private CommentService commentService;

    @DisplayName("댓글을 작성한다.")
    @Test
    void createComment() throws Exception {
        CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder()
            .memberId(PERSIST_MEMBER.getId())
            .content("댓글입니다.")
            .location(GANGNAM_STATION)
            .build();

        String request = objectMapper.writeValueAsString(commentCreateRequest);
        when(commentService.createComment(any())).thenReturn(1L);

        this.mockMvc.perform(post("/comments")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(status().isCreated())
            .andDo(print());

        verify(commentService).createComment(any());
    }

    @DisplayName("댓글을 삭제한다.")
    @Test
    void deleteComment() throws Exception {
        doNothing().when(commentService)
            .deleteComment(PERSIST_COMMENT.getId());
        this.mockMvc.perform(
            delete("/comments/" + PERSIST_COMMENT.getId()))
            .andExpect(status().isNoContent())
            .andDo(print());
    }
}