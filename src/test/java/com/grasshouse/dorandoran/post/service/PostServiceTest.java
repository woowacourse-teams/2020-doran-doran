package com.grasshouse.dorandoran.post.service;

import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static com.grasshouse.dorandoran.fixture.MemberFixture.MEMBER;
import static com.grasshouse.dorandoran.fixture.PostFixture.POST;
import static org.assertj.core.api.Assertions.assertThat;

import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import com.grasshouse.dorandoran.post.service.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.service.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(MEMBER);
    }

    @DisplayName("글을 작성한다.")
    @Test
    void createPostTest() {
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
            .author(MEMBER)
            .content("내용")
            .location(JAMSIL_STATION)
            .build();

        PostCreateResponse createResponse = postService.createPost(postCreateRequest);
        assertThat(createResponse.getId()).isNotNull();
    }

    @DisplayName("하나의 글을 조회한다.")
    @Test
    void showPost() {
        Post persistPost = postRepository.save(POST);

        PostResponse postResponse = postService.showPost(persistPost.getId());

        assertThat(postResponse.getContent()).isEqualTo(persistPost.getContent());
    }

    @DisplayName("전체 글을 조회한다.")
    @Test
    void showPostsTest() {
        Post persistPost = postRepository.save(POST);

        List<PostResponse> postResponses = postService.showPosts();

        assertThat(postResponses).hasSize(1);
        assertThat(postResponses.get(0).getContent()).isEqualTo(persistPost.getContent());
    }

    @DisplayName("글을 삭제한다.")
    @Test
    void deletePostTest() {
        Post persistPost = postRepository.save(POST);
        assertThat(postRepository.findAll()).hasSize(1);

        postService.deletePost(persistPost.getId());
        assertThat(postRepository.findAll()).hasSize(0);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }
}