package com.grasshouse.dorandoran.post.service;

import static com.grasshouse.dorandoran.fixture.AddressFixture.ADDRESS;
import static com.grasshouse.dorandoran.fixture.AddressFixture.AUTHOR_ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static org.assertj.core.api.Assertions.assertThat;

import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostRepository;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostSearchServiceTest {

    @Autowired
    private PostSearchService postSearchService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;
    private Post post1;
    private Post post2;
    private Post post3;

    private static final String SEARCH_KEYWORD = "오늘";

    @BeforeEach
    void setUp() {
        member = Member.builder()
            .nickname("오구")
            .build();

        memberRepository.save(member);

        post1 = Post.builder()
            .author(member)
            .authorAddress(AUTHOR_ADDRESS)
            .content(SEARCH_KEYWORD + " 날씨 어떄요")
            .location(JAMSIL_STATION)
            .address(ADDRESS)
            .build();

        post2 = Post.builder()
            .author(member)
            .authorAddress(AUTHOR_ADDRESS)
            .content("저는 " + SEARCH_KEYWORD + " 냉면이 먹고 싶어요")
            .location(JAMSIL_STATION)
            .address(ADDRESS)
            .build();

        post3 = Post.builder()
            .author(member)
            .authorAddress(AUTHOR_ADDRESS)
            .content("바쁘다...")
            .location(JAMSIL_STATION)
            .address(ADDRESS)
            .build();

        postRepository.saveAll(Arrays.asList(post1, post2, post3));

    }

    @DisplayName("키워드로 검색된 글을 반환한다.")
    @Test
    void showSearchResults() {
        assertThat(postSearchService.showSearchResults(SEARCH_KEYWORD)).hasSize(2);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }
}