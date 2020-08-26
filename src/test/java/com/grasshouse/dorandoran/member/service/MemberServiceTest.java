package com.grasshouse.dorandoran.member.service;

import static com.grasshouse.dorandoran.fixture.AddressFixture.ADDRESS;
import static com.grasshouse.dorandoran.fixture.AddressFixture.AUTHOR_ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static org.assertj.core.api.Assertions.assertThat;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.member.service.dto.MemberUpdateRequest;
import com.grasshouse.dorandoran.member.service.dto.MemberUpdateResponse;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

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
            .nickname("nickname")
            .oAuthId("oauthId")
            .build();

        memberRepository.save(member);
    }

    @DisplayName("사용자의 닉네임을 업데이트한다.")
    @Test
    void update() {
        MemberUpdateRequest request = new MemberUpdateRequest("new nickname");

        MemberUpdateResponse response = memberService.update(member, request);

        assertThat(response.getNickname()).isEqualTo(request.getNickname());
    }

    @DisplayName("사용자의 정보를 삭제한다.")
    @Test
    void delete() {
        assertThat(memberRepository.findAll()).hasSize(1);

        memberService.delete(member);

        assertThat(memberRepository.findAll()).hasSize(0);
    }

    @DisplayName("사용자의 정보를 삭제할 때 글도 함께 삭제된다.")
    @Test
    void deleteMemberWithPosts() {
        Post post = Post.builder()
            .author(member)
            .address(ADDRESS)
            .authorAddress(AUTHOR_ADDRESS)
            .location(JAMSIL_STATION)
            .content("첫 번째 글")
            .build();
        postRepository.save(post);

        assertThat(postRepository.findAll()).hasSize(1);

        memberRepository.deleteById(member.getId());

        assertThat(postRepository.findAll()).hasSize(0);
    }

    @DisplayName("사용자의 정보를 삭제할 때 댓글도 함께 삭제된다.")
    @Test
    void deleteMemberWithComments() {
        Post post = Post.builder()
            .author(member)
            .address(ADDRESS)
            .authorAddress(AUTHOR_ADDRESS)
            .location(JAMSIL_STATION)
            .content("첫 번째 글")
            .build();
        postRepository.save(post);

        Comment comment = Comment.builder().post(post).author(member).content("댓글").distance(0.0).build();
        commentRepository.save(comment);

        assertThat(commentRepository.findAll()).hasSize(1);

        memberRepository.deleteById(member.getId());

        assertThat(commentRepository.findAll()).hasSize(0);
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }
}