package com.grasshouse.dorandoran.comment.service;

import static com.grasshouse.dorandoran.fixture.AddressFixture.ADDRESS;
import static com.grasshouse.dorandoran.fixture.AddressFixture.AUTHOR_ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.GANGNAM_STATION;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.comment.service.dto.CommentCreateRequest;
import com.grasshouse.dorandoran.common.exception.CommentNotFoundException;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    private Post post;

    @BeforeEach
    void setUp() {
        member = Member.builder()
            .nickname("오구")
            .build();

        post = Post.builder()
            .author(member)
            .authorAddress(AUTHOR_ADDRESS)
            .content("내용")
            .address(ADDRESS)
            .location(JAMSIL_STATION)
            .build();

        memberRepository.save(member);
        postRepository.save(post);
    }

    @DisplayName("댓글을 작성한다.")
    @Test
    void createComment() {
        CommentCreateRequest request = CommentCreateRequest.builder()
            .memberId(member.getId())
            .postId(post.getId())
            .content("댓글댓글")
            .location(GANGNAM_STATION)
            .build();

        Long commentId = commentService.createComment(request);

        assertThat(commentId).isNotNull();
    }

    @DisplayName("댓글을 삭제한다.")
    @Test
    void deleteComment() {
        Comment comment = Comment.builder()
            .author(member)
            .post(post)
            .content("댓글입니다.")
            .distance(1.0)
            .build();

        Comment persistComment = commentRepository.save(comment);
        assertThat(commentRepository.findAll()).hasSize(1);

        commentService.deleteComment(persistComment.getId());
        assertThat(commentRepository.findAll()).hasSize(0);
    }

    @DisplayName("댓글 내용이 120자를 넘을 경우 예외를 발생시킨다.")
    @Test
    void maxLengthComment() {
        Comment comment = Comment.builder()
            .author(member)
            .post(post)
            .content("댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다"
                + "댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다"
                + "댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다꽝")
            .distance(1.0)
            .build();

        assertThatThrownBy(() -> commentRepository.save(comment))
            .isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("120자");
    }

    @DisplayName("댓글 작성할 때 createdAt 필드가 추가된다.")
    @Test
    void checkCommentCreatedAt() {
        CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder()
            .memberId(member.getId())
            .postId(post.getId())
            .content("내용")
            .location(JAMSIL_STATION)
            .build();

        Comment createdComment = commentRepository
            .findById(commentService.createComment(commentCreateRequest))
            .orElseThrow(CommentNotFoundException::new);
        assertThat(createdComment.getCreatedAt()).isNotNull();
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }
}
