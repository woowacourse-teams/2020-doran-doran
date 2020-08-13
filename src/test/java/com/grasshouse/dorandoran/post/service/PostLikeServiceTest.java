package com.grasshouse.dorandoran.post.service;

import static com.grasshouse.dorandoran.fixture.AddressFixture.ADDRESS;
import static com.grasshouse.dorandoran.fixture.AddressFixture.AUTHOR_ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.grasshouse.dorandoran.common.exception.PostLikeAlreadyExistsException;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.domain.PostLike;
import com.grasshouse.dorandoran.post.repository.PostLikeRepository;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import com.grasshouse.dorandoran.post.repository.PostRepositorySupport;
import com.grasshouse.dorandoran.post.service.dto.PostLikeCreateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostLikeServiceTest {

    @Autowired
    private PostLikeService postLikeService;

    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepositorySupport postRepositorySupport;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    Member author;

    Member postLiker;

    Post post;

    @BeforeEach
    void setUp() {
        author = Member.builder()
            .nickname("오구")
            .build();

        postLiker = Member.builder()
            .nickname("게시물좋아요누르고싶은사람")
            .build();

        post = Post.builder()
            .author(author)
            .authorAddress(AUTHOR_ADDRESS)
            .content("내용")
            .address(ADDRESS)
            .location(JAMSIL_STATION)
            .build();

        memberRepository.save(author);
        memberRepository.save(postLiker);
        postRepository.save(post);
    }

    @DisplayName("게시글에 좋아요를 추가한다.")
    @Test
    void createPostLike() {
        PostLikeCreateRequest request = PostLikeCreateRequest.builder()
            .memberId(postLiker.getId())
            .postId(post.getId())
            .build();

        postLikeService.createPostLike(request);
        Post persistPost = postRepositorySupport.findPostContainingLikes(post.getId());

        assertThat(persistPost.getLikes()).hasSize(1);
    }

    @DisplayName("[예외] 게시글에 이미 좋아요가 추가됐을 때(같은 member가 같은 post에) 또 추가한다.")
    @Test
    void duplicatePostLike() {
        PostLikeCreateRequest firstRequest = PostLikeCreateRequest.builder()
            .memberId(postLiker.getId())
            .postId(post.getId())
            .build();
        postLikeService.createPostLike(firstRequest);

        PostLikeCreateRequest duplicateRequest = PostLikeCreateRequest.builder()
            .memberId(postLiker.getId())
            .postId(post.getId())
            .build();
        assertThatThrownBy(() -> postLikeService.createPostLike(duplicateRequest))
            .isInstanceOf(PostLikeAlreadyExistsException.class);
    }

    @DisplayName("게시글의 좋아요를 취소(삭제)한다.")
    @Test
    void deletePostLike() {
        PostLike postLike = PostLike.builder()
            .memberId(postLiker.getId())
            .post(post)
            .build();

        PostLike persistPostLike = postLikeRepository.save(postLike);
        assertThat(postLikeRepository.findAll()).hasSize(1);

        postLikeService.deletePostLike(persistPostLike.getId());
        assertThat(postLikeRepository.findAll()).hasSize(0);
    }

    @DisplayName("게시글을 삭제할 때 좋아요도 같이 삭제된다.")
    @Test
    void deleteCommentWithCommentLike() {
        PostLikeCreateRequest request = PostLikeCreateRequest.builder()
            .memberId(postLiker.getId())
            .postId(post.getId())
            .build();

        postLikeService.createPostLike(request);
        Post persistPost = postRepositorySupport.findPostContainingLikes(post.getId());
        assertThat(persistPost.getLikes()).hasSize(1);

        postService.deletePost(persistPost.getId());
        assertThat(postRepository.findAll()).hasSize(0);
        assertThat(postLikeRepository.findAll()).hasSize(0);
    }

    @AfterEach
    void tearDown() {
        postLikeRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }
}
