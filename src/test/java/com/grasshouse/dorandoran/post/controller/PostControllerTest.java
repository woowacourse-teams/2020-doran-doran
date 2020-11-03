package com.grasshouse.dorandoran.post.controller;

import static com.grasshouse.dorandoran.fixture.AddressFixture.AUTHOR_ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.GANGNAM_STATION;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static com.grasshouse.dorandoran.fixture.MemberFixture.PERSIST_MEMBER;
import static com.grasshouse.dorandoran.fixture.PostFixture.PERSIST_POST;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grasshouse.dorandoran.common.CommonControllerTest;
import com.grasshouse.dorandoran.common.config.jwt.JwtTokenProvider;
import com.grasshouse.dorandoran.common.exception.MemberMismatchException;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.post.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.dto.PostResponse;
import com.grasshouse.dorandoran.post.service.PostService;
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
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private MemberRepository memberRepository;

    @DisplayName("로그인한 사용자가 글을 작성한다.")
    @Test
    void createPostWithLoginUser() throws Exception {
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
            .content("new post")
            .location(JAMSIL_STATION)
            .authorAddress(AUTHOR_ADDRESS)
            .build();

        PostCreateResponse postCreateResponse = new PostCreateResponse(1L);

        String request = objectMapper.writeValueAsString(postCreateRequest);
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getSubject(anyString())).thenReturn("id");
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);
        when(postService.createPost(any(), any())).thenReturn(postCreateResponse);

        this.mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(status().isCreated())
            .andDo(print());

        verify(postService).createPost(any(), any());
    }

    @DisplayName("[예외] 로그인 하지 않은 사용자가 글을 작성한다.")
    @Test
    void createPostWithoutLogin() throws Exception {
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
            .content("new post")
            .location(JAMSIL_STATION)
            .authorAddress(AUTHOR_ADDRESS)
            .build();

        String request = objectMapper.writeValueAsString(postCreateRequest);

        this.mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(status().isUnauthorized())
            .andDo(print());
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

    @DisplayName("로그인한 사용자가 자신의 글을 삭제한다.")
    @Test
    void deletePostWithLoginUser() throws Exception {
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getSubject(anyString())).thenReturn("id");
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);
        doNothing().when(postService).deletePost(anyLong(), any());

        this.mockMvc.perform(delete("/posts/" + PERSIST_POST.getId()))
            .andExpect(status().isNoContent())
            .andDo(print());

        verify(postService).deletePost(anyLong(), any());
    }

    @DisplayName("[예외] 로그인 하지 않은 사용자가 글을 삭제한다.")
    @Test
    void deletePostWithoutLogin() throws Exception {
        this.mockMvc.perform(delete("/posts/" + PERSIST_POST.getId()))
            .andExpect(status().isUnauthorized())
            .andDo(print());
    }

    @DisplayName("[예외] 로그인한 사용자가 다른 사용자의 글을 삭제한다.")
    @Test
    void deleteAnotherAuthorsPost() throws Exception {
        Member anotherMember = Member.builder()
            .id(7L)
            .nickname("엘리")
            .build();

        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getSubject(anyString())).thenReturn("id");
        when(memberRepository.findByoAuthId(anyString())).thenReturn(anotherMember);
        doThrow(MemberMismatchException.class).when(postService).deletePost(anyLong(), any());

        this.mockMvc.perform(delete("/posts/" + PERSIST_POST.getId()))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("[예외] PostCreateRequest DTO의 내용이 200자를 넘는다.")
    @Test
    void postCreateDtoTooLong() throws Exception {
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
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
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getSubject(anyString())).thenReturn("id");
        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);
        when(postService.createPost(any(), any())).thenReturn(postCreateResponse);

        this.mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(result -> assertTrue(
                result.getResolvedException() instanceof MethodArgumentNotValidException))
            .andDo(print());
    }

    private List<PostResponse> postResponses() {
        return Arrays.asList(PostResponse.from(PERSIST_POST));
    }
}
