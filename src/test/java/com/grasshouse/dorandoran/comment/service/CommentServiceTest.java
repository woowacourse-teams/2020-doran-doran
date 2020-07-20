package com.grasshouse.dorandoran.comment.service;

import static com.grasshouse.dorandoran.fixture.AddressFixture.ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.GANGNAM_STATION;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static org.assertj.core.api.Assertions.assertThat;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.comment.service.dto.CommentCreateRequest;
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

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }
}