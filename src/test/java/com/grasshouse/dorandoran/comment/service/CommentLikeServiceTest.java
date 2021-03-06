package com.grasshouse.dorandoran.comment.service;

import static com.grasshouse.dorandoran.fixture.AddressFixture.ADDRESS;
import static com.grasshouse.dorandoran.fixture.AddressFixture.AUTHOR_ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.domain.CommentLike;
import com.grasshouse.dorandoran.comment.dto.CommentLikeCreateRequest;
import com.grasshouse.dorandoran.comment.repository.CommentLikeRepository;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.comment.repository.CommentRepositorySupport;
import com.grasshouse.dorandoran.common.baseentity.EntityStatus;
import com.grasshouse.dorandoran.common.exception.CommentLikeAlreadyExistsException;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentLikeServiceTest {

    @Autowired
    private CommentLikeService commentLikeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private CommentRepositorySupport commentRepositorySupport;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    private Member author;

    private Member commentLiker;

    private Post post;

    private Comment comment;

    @BeforeEach
    void setUp() {
        author = Member.builder()
            .nickname("오구")
            .build();

        commentLiker = Member.builder()
            .nickname("좋아요누르고싶은사람")
            .build();

        post = Post.builder()
            .author(author)
            .authorAddress(AUTHOR_ADDRESS)
            .content("내용")
            .address(ADDRESS)
            .location(JAMSIL_STATION)
            .build();

        comment = Comment.builder()
            .author(author)
            .content("댓글댓글댓글")
            .post(post)
            .distance(1.0)
            .build();

        memberRepository.save(author);
        memberRepository.save(commentLiker);
        postRepository.save(post);
        commentRepository.save(comment);
    }

    @DisplayName("댓글에 좋아요를 추가한다.")
    @Test
    void createCommentLike() {
        CommentLikeCreateRequest request = CommentLikeCreateRequest.builder()
            .commentId(comment.getId())
            .build();

        commentLikeService.createCommentLike(request, commentLiker);
        Comment persistComment = commentRepositorySupport
            .findCommentContainingLikes(comment.getId());

        assertThat(persistComment.getLikes()).hasSize(1);
    }

    @DisplayName("[예외] 댓글에 이미 좋아요가 추가됐을 때(같은 member가 같은 comment에) 또 추가한다.")
    @Test
    void duplicateCommentLike() {
        CommentLikeCreateRequest firstRequest = CommentLikeCreateRequest.builder()
            .commentId(comment.getId())
            .build();
        commentLikeService.createCommentLike(firstRequest, commentLiker);

        CommentLikeCreateRequest duplicateRequest = CommentLikeCreateRequest.builder()
            .commentId(comment.getId())
            .build();
        assertThatThrownBy(
            () -> commentLikeService.createCommentLike(duplicateRequest, commentLiker))
            .isInstanceOf(CommentLikeAlreadyExistsException.class);
    }

    @DisplayName("댓글에 좋아요를 삭제한다.")
    @Test
    void deleteCommentLike() {
        CommentLike commentLike = CommentLike.builder()
            .memberId(commentLiker.getId())
            .comment(comment)
            .build();

        CommentLike persistCommentLike = commentLikeRepository.save(commentLike);
        assertThat(commentLikeRepository.findAll()).hasSize(1);

        commentLikeService.deleteCommentLike(persistCommentLike.getId(), commentLiker);

        assertThat(commentLikeRepository.findAll()).hasSize(0);
    }

    @DisplayName("댓글이 삭제 상태로 변경되면 좋아요가 삭제된다.")
    @Test
    void deleteCommentWithCommentLike() {
        CommentLikeCreateRequest request = CommentLikeCreateRequest.builder()
            .commentId(comment.getId())
            .build();

        commentLikeService.createCommentLike(request, commentLiker);
        Comment persistComment = commentRepositorySupport
            .findCommentContainingLikes(comment.getId());
        assertThat(persistComment.getLikes()).hasSize(1);

        commentService.deleteComment(persistComment.getId(), author);

        Comment deletedComment = commentRepository.findAll().get(0);
        assertThat(deletedComment.getStatus()).isEqualTo(EntityStatus.DELETED);
        assertThat(commentLikeRepository.findAll()).hasSize(0);
    }

    @AfterEach
    void tearDown() {
        commentLikeRepository.deleteAll();
        commentRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }
}