package com.grasshouse.dorandoran.post.service;

import static com.grasshouse.dorandoran.fixture.AddressFixture.ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static org.assertj.core.api.Assertions.assertThat;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.member.domain.Member;
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

    @Autowired
    private CommentRepository commentRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
            .nickname("오구")
            .build();

        memberRepository.save(member);
    }

    @DisplayName("글을 작성한다.")
    @Test
    void createPostTest() {
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
            .memberId(member.getId())
            .content("내용")
            .location(JAMSIL_STATION)
            .build();

        PostCreateResponse createResponse = postService.createPost(postCreateRequest);
        assertThat(createResponse.getId()).isNotNull();
    }

    @DisplayName("하나의 글을 조회한다.")
    @Test
    void showPost() {
        Post post = dummyPost();

        Post persistPost = postRepository.save(post);

        PostResponse postResponse = postService.showPost(persistPost.getId());

        assertThat(postResponse.getContent()).isEqualTo(persistPost.getContent());
    }

    @DisplayName("전체 글을 조회한다.")
    @Test
    void showPostsTest() {
        Post post = dummyPost();

        Post persistPost = postRepository.save(post);

        List<PostResponse> postResponses = postService.showPosts();

        assertThat(postResponses).hasSize(1);
        assertThat(postResponses.get(0).getContent()).isEqualTo(persistPost.getContent());
    }

    @DisplayName("글을 삭제한다.")
    @Test
    void deletePostTest() {
        Post post = dummyPost();

        Post persistPost = postRepository.save(post);

        assertThat(postRepository.findAll()).hasSize(1);

        postService.deletePost(persistPost.getId());
        assertThat(postRepository.findAll()).hasSize(0);
    }

    @DisplayName("글을 삭제 시 댓글도 삭제된다.")
    @Test
    void deletePostWithCommentTest() {
        Post post = dummyPost();
        Post persistPost = postRepository.save(post);

        Comment comment = Comment.builder()
            .author(member)
            .content("코맨트")
            .post(persistPost)
            .distance(5.7)
            .build();
        commentRepository.save(comment);

        assertThat(commentRepository.findAll()).hasSize(1);

        postService.deletePost(persistPost.getId());
        assertThat(commentRepository.findAll()).hasSize(0);
    }

    private Post dummyPost() {
        return Post.builder()
            .author(member)
            .content("내용")
            .address(ADDRESS)
            .location(JAMSIL_STATION)
            .build();
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }
}