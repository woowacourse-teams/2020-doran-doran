package com.grasshouse.dorandoran.post.controller;

import static com.grasshouse.dorandoran.fixture.AddressFixture.AUTHOR_ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.GANGNAM_STATION;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static com.grasshouse.dorandoran.fixture.MemberFixture.PERSIST_MEMBER;
import static com.grasshouse.dorandoran.fixture.PostFixture.PERSIST_POST;
import static com.grasshouse.dorandoran.fixture.PostLikeFixture.PERSIST_POST_LIKE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grasshouse.dorandoran.common.CommonControllerTest;
import com.grasshouse.dorandoran.post.service.PostLikeService;
import com.grasshouse.dorandoran.post.service.PostService;
import com.grasshouse.dorandoran.post.service.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.service.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;

class PostControllerTest extends CommonControllerTest {

    @MockBean
    private PostService postService;

    @MockBean
    private PostLikeService postLikeService;

    @DisplayName("글을 작성한다.")
    @Test
    void createPost() throws Exception {
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
            .memberId(PERSIST_MEMBER.getId())
            .content("new post")
            .location(JAMSIL_STATION)
            .authorAddress(AUTHOR_ADDRESS)
            .build();

        PostCreateResponse postCreateResponse = new PostCreateResponse(1L);

        String request = objectMapper.writeValueAsString(postCreateRequest);
        when(postService.createPost(any())).thenReturn(postCreateResponse);

        this.mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(status().isCreated())
            .andDo(print());

        verify(postService).createPost(any());
    }

    @DisplayName("글 하나를 조회한다.")
    @Test
    void showPost() throws Exception {
        when(postService.showPost(anyLong())).thenReturn(PostResponse.from(PERSIST_POST));

        this.mockMvc.perform(get("/posts/" + PERSIST_POST.getId())
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.content").isNotEmpty())
            .andExpect(jsonPath("$.location").isNotEmpty())
            .andExpect(jsonPath("$.comments").isArray())
            .andDo(print());

        verify(postService).showPost(any());
    }

    @DisplayName("글 목록을 조회한다.")
    @Test
    void showPosts() throws Exception {
        when(postService.showPosts()).thenReturn(postResponses());

        this.mockMvc.perform(get("/posts")
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].id").isNotEmpty())
            .andExpect(jsonPath("$[*].content").isNotEmpty())
            .andExpect(jsonPath("$[*].location").isNotEmpty())
            .andDo(print());

        verify(postService).showPosts();
    }

    @DisplayName("글을 삭제한다.")
    @Test
    void deletePost() throws Exception {
        doNothing().when(postService).deletePost(anyLong());

        this.mockMvc.perform(delete("/posts/" + PERSIST_POST.getId()))
            .andExpect(status().isNoContent())
            .andDo(print());

        verify(postService).deletePost(anyLong());
    }

    @DisplayName("[예외] PostCreateRequest DTO의 내용이 200자를 넘는다.")
    @Test
    void postCreateDtoTooLong() throws Exception {
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
            .memberId(PERSIST_MEMBER.getId())
            .authorAddress(AUTHOR_ADDRESS)
            .content("안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요"
                + "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요"
                + "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요"
                + "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요"
                + "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요꽝꽝")
            .location(GANGNAM_STATION)
            .build();

        PostCreateResponse postCreateResponse = new PostCreateResponse(1L);

        String request = objectMapper.writeValueAsString(postCreateRequest);
        when(postService.createPost(any())).thenReturn(postCreateResponse);

        this.mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(result -> assertTrue(
                result.getResolvedException() instanceof MethodArgumentNotValidException))
            .andDo(print());
    }

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

    private List<PostResponse> postResponses() {
        return Arrays.asList(PostResponse.from(PERSIST_POST));
    }
}
