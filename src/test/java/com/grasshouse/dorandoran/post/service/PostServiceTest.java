package com.grasshouse.dorandoran.post.service;

import static com.grasshouse.dorandoran.fixture.AddressFixture.ADDRESS;
import static com.grasshouse.dorandoran.fixture.AddressFixture.AUTHOR_ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.GANGNAM_STATION;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.common.baseentity.EntityStatus;
import com.grasshouse.dorandoran.common.exception.PostNotFoundException;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.dto.PostBoundsRequest;
import com.grasshouse.dorandoran.post.dto.PostCreateRequest;
import com.grasshouse.dorandoran.post.dto.PostCreateResponse;
import com.grasshouse.dorandoran.post.dto.PostResponse;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import java.util.List;
import javax.swing.text.html.parser.Entity;
import javax.validation.ConstraintViolationException;
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
            .authorAddress(AUTHOR_ADDRESS)
            .content("내용")
            .location(JAMSIL_STATION)
            .build();

        PostCreateResponse createResponse = postService.createPost(postCreateRequest, member);
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

        postService.deletePost(persistPost.getId(), member);

        Post savedPost = postRepository.findAll().get(0);
        assertThat(savedPost.getStatus()).isEqualTo(EntityStatus.DELETED);
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

        postService.deletePost(persistPost.getId(), member);

        Post savedPost = postRepository.findAll().get(0);
        Comment savedComment = commentRepository.findAll().get(0);
        assertThat(savedPost.getStatus()).isEqualTo(EntityStatus.DELETED);
        assertThat(savedComment.getPost().getId()).isEqualTo(savedPost.getId());
        assertThat(savedComment.getStatus()).isEqualTo(EntityStatus.DELETED);
    }

    @DisplayName("[예외] 글 내용이 200자를 넘는다.")
    @Test
    void postTooLong() {
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
            .memberId(member.getId())
            .authorAddress(AUTHOR_ADDRESS)
            .content("안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요"
                + "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요"
                + "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요"
                + "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요"
                + "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요꽝꽝")
            .location(GANGNAM_STATION)
            .build();

        assertThatThrownBy(() -> postService.createPost(postCreateRequest, member))
            .isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("200자");
    }

    @DisplayName("글을 작성할 때 createdAt 필드가 추가된다.")
    @Test
    void checkPostCreatedAt() {
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
            .memberId(member.getId())
            .authorAddress(AUTHOR_ADDRESS)
            .content("내용")
            .location(JAMSIL_STATION)
            .build();

        PostCreateResponse createResponse = postService.createPost(postCreateRequest, member);
        Post createdPost = postRepository.findById(createResponse.getId())
            .orElseThrow(PostNotFoundException::new);
        assertThat(createdPost.getCreatedAt()).isNotNull();
    }

    private Post dummyPost() {
        return Post.builder()
            .author(member)
            .content("내용")
            .address(ADDRESS)
            .authorAddress(AUTHOR_ADDRESS)
            .location(JAMSIL_STATION)
            .build();
    }

    private Post dummyPost2() {
        return Post.builder()
            .author(member)
            .content("내용2")
            .address(ADDRESS)
            .authorAddress(AUTHOR_ADDRESS)
            .location(GANGNAM_STATION)
            .build();
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }
}
