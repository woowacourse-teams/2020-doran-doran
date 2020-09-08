package com.grasshouse.dorandoran.post.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.fixture.AddressFixture;
import com.grasshouse.dorandoran.fixture.LocationFixture;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.post.domain.Post;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostRepositorySupportTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepositorySupport postRepositorySupport;

    @DisplayName("댓글이 없는 글을 조회한다.")
    @Test
    void findPostByIdWithoutComments() {
        Member member = SAVE_MEMBER();

        Post post = SAVE_POST(member);

        assertThat(post.getComments()).hasSize(0);

        Post persistPost = postRepository.findById(post.getId()).get();

        Post postById = postRepositorySupport.findPostById(persistPost.getId());
        assertThat(postById.getComments()).hasSize(0);
    }

    @DisplayName("삭제된 댓글만 있는 글을 조회한다.")
    @Test
    void findPostByIdWithComments1() {
        Member member = SAVE_MEMBER();

        Post post = SAVE_POST(member);

        Comment comment1 = SAVE_COMMENT(member, post, "댓글1");

        assertThat(post.getComments()).hasSize(1);

        post.removeComment(comment1);
        postRepository.save(post);
        commentRepository.save(comment1);

        Post postById = postRepositorySupport.findPostById(post.getId());

        assertThat(postById.getComments()).hasSize(0);
    }

    @DisplayName("삭제되지 않은 댓글과 함께 글을 조회한다.")
    @Test
    void findPostByIdWithComments2() {
        Member member = SAVE_MEMBER();

        Post post = SAVE_POST(member);

        Comment comment1 = SAVE_COMMENT(member, post, "댓글1");
        Comment comment2 = SAVE_COMMENT(member, post, "댓글2");

        assertThat(post.getComments()).hasSize(2);

        post.removeComment(comment1);
        postRepository.save(post);
        commentRepository.save(comment1);

        Post postById = postRepositorySupport.findPostById(post.getId());

        assertThat(postById.getComments()).hasSize(1);
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    private Member SAVE_MEMBER() {
        Member member = Member.builder()
            .oAuthId("OAUTH_ID")
            .nickname("NICKNAME")
            .build();
        memberRepository.save(member);
        return member;
    }

    private Post SAVE_POST(Member member) {
        Post post = Post.builder()
            .content("글")
            .location(LocationFixture.JAMSIL_STATION)
            .author(member)
            .address(AddressFixture.ADDRESS)
            .authorAddress(AddressFixture.AUTHOR_ADDRESS).build();
        return postRepository.save(post);
    }

    private Comment SAVE_COMMENT(Member member, Post post, String content) {
        Comment comment = Comment.builder()
            .author(member)
            .post(post)
            .content(content)
            .distance(0.0)
            .build();
        post.addComment(comment);
        return commentRepository.save(comment);
    }
}