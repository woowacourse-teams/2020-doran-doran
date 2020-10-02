package com.grasshouse.dorandoran.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.comment.repository.CommentRepository;
import com.grasshouse.dorandoran.fixture.AddressFixture;
import com.grasshouse.dorandoran.fixture.LocationFixture;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.domain.PostLike;
import com.grasshouse.dorandoran.post.dto.PostFilterRequest;
import com.grasshouse.dorandoran.post.dto.PostResponse;
import com.grasshouse.dorandoran.post.repository.PostLikeRepository;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
class PostFilterServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostFilterService postFilterService;

    @Autowired
    private PostFilterService PostFilterService;

    static Stream<Arguments> generateDays() {
        return Stream.of(
            Arguments.of(toDate("2020-05-31 00:00:00"), toDate("2020-06-30 23:59:59"), 4),
            Arguments.of(toDate("2020-06-02 00:00:00"), toDate("2020-06-30 23:59:59"), 3),
            Arguments.of(toDate("2020-06-04 00:00:00"), toDate("2020-06-30 23:59:59"), 2),
            Arguments.of(toDate("2020-06-11 00:00:00"), toDate("2020-06-30 23:59:59"), 1)
        );
    }

    @Sql(value = "/dateTimePost.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/dateTimePostDelete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("키워드로 검색된 글들을 반환한다.")
    @Test
    void showSearchResultsByKeyword() {
        PostFilterRequest request = new PostFilterRequest("오늘", null, null);

        assertThat(PostFilterService.showFilteredPosts(request)).hasSize(2);
    }

    @Sql(value = "/dateTimePost.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/dateTimePostDelete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("특정 시간 사이의 글들을 반환한다.")
    @ParameterizedTest
    @MethodSource("generateDays")
    void showSearchResultsByDate(LocalDateTime startDate, LocalDateTime endDate, int searchResult) {
        PostFilterRequest request = new PostFilterRequest(null, startDate, endDate);
        assertThat(PostFilterService.showFilteredPosts(request))
            .hasSize(searchResult);
    }

    @DisplayName("post.comments, post.likes를 잘 가져오는지 테스트")
    @Test
    void findPostWithKeywordAndDate() {
        Member member1 = SAVE_MEMBER("멤버1", "oAuthId1");
        Member member2 = SAVE_MEMBER("멤버2", "oAuthId2");
        Post post = SAVE_POST(member1);
        SAVE_COMMENT(member1, post, "댓글1");
        SAVE_COMMENT(member1, post, "댓글2");
        SAVE_POST_LIKE(member1.getId(), post);
        SAVE_POST_LIKE(member2.getId(), post);

        PostFilterRequest request = new PostFilterRequest(null, null, null);
        List<PostResponse> postResponses = postFilterService.showFilteredPosts(request);

        assertThat(postResponses).hasSize(1);
        assertAll(
            () -> {
                assertThat(postResponses.get(0).getComments()).hasSize(2);
                assertThat(postResponses.get(0).getLikes()).hasSize(2);
            }
        );
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    private static LocalDateTime toDate(String value) {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private Member SAVE_MEMBER(String nickname, String oAuthId) {
        Member member = Member.builder()
            .nickname(nickname)
            .oAuthId(oAuthId)
            .build();
        return memberRepository.save(member);
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

    private PostLike SAVE_POST_LIKE(Long memberId, Post post) {
        PostLike postLike = PostLike.builder()
            .memberId(memberId)
            .post(post)
            .build();
        return postLikeRepository.save(postLike);
    }
}
