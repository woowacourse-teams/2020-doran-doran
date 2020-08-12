package com.grasshouse.dorandoran.comment.service;

import static com.grasshouse.dorandoran.fixture.AddressFixture.ADDRESS;
import static com.grasshouse.dorandoran.fixture.AddressFixture.AUTHOR_ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static org.assertj.core.api.Assertions.assertThat;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.domain.CommentLike;
import com.grasshouse.dorandoran.comment.repository.CommentLikeRepository;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.comment.repository.CommentRepositorySupport;
import com.grasshouse.dorandoran.comment.service.dto.CommentLikeCreateRequest;
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
            .memberId(commentLiker.getId())
            .commentId(comment.getId())
            .build();

        commentLikeService.createCommentLike(request);
        Comment persistComment = commentRepositorySupport.findCommentWithLikes(comment.getId());

        assertThat(persistComment.getLikes()).hasSize(1);
    }

    @DisplayName("댓글에 추가한 좋아요를 취소(삭제)한다.")
    @Test
    void deleteCommentLike() {
        CommentLike commentLike = CommentLike.builder()
            .memberId(commentLiker.getId())
            .comment(comment)
            .build();

        CommentLike persistCommentLike = commentLikeRepository.save(commentLike);
        assertThat(commentLikeRepository.findAll()).hasSize(1);

        commentLikeService.deleteCommentLike(persistCommentLike.getId());
        assertThat(commentLikeRepository.findAll()).hasSize(0);
    }

    @DisplayName("댓글을 삭제할 때 좋아요도 같이 삭제된다.")
    @Test
    void deleteCommentWithCommentLike() {
        CommentLikeCreateRequest request = CommentLikeCreateRequest.builder()
            .memberId(commentLiker.getId())
            .commentId(comment.getId())
            .build();

        commentLikeService.createCommentLike(request);
        Comment persistComment = commentRepositorySupport.findCommentWithLikes(comment.getId());
        assertThat(persistComment.getLikes()).hasSize(1);

        commentService.deleteComment(persistComment.getId());
        assertThat(commentRepository.findAll()).hasSize(0);
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