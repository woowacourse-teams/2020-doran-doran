package com.grasshouse.dorandoran.post.service;

import static com.grasshouse.dorandoran.fixture.LocationFixture.LOCATION;
import static com.grasshouse.dorandoran.fixture.MemberFixture.MEMBER;
import static com.grasshouse.dorandoran.fixture.PostFixture.POST;
import static org.assertj.core.api.Assertions.assertThat;

import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import com.grasshouse.dorandoran.post.service.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.service.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import java.util.List;
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

    @DisplayName("글이 작성된다.")
    @Test
    void createPostTest() {
        Member persistMember = memberRepository.save(MEMBER);
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
            .author(persistMember)
            .content("내용")
            .location(LOCATION)
            .build();

        PostCreateResponse createResponse = postService.createPost(postCreateRequest);
        assertThat(createResponse.getId()).isNotNull();
    }

    @DisplayName("글을 조회한다.")
    @Test
    void showPostsTest() {
        memberRepository.save(MEMBER);
        postRepository.save(POST);

        List<PostResponse> postResponses = postService.showPosts();

        assertThat(postResponses).hasSize(1);
        assertThat(postResponses.get(0).getContent()).isEqualTo(POST.getContent());
    }

    @DisplayName("글을 삭제한다.")
    @Test
    void deletePostTest() {
        memberRepository.save(MEMBER);
        postRepository.save(POST);

        assertThat(postRepository.findAll()).hasSize(1);

        postService.deletePost(POST.getId());

        assertThat(postRepository.findAll()).hasSize(0);
    }
}