package com.grasshouse.dorandoran.post.service;

import static com.grasshouse.dorandoran.fixture.AddressFixture.ADDRESS;
import static com.grasshouse.dorandoran.fixture.AddressFixture.AUTHOR_ADDRESS;
import static com.grasshouse.dorandoran.fixture.LocationFixture.JAMSIL_STATION;
import static org.assertj.core.api.Assertions.assertThat;

import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.post.domain.Post;
import com.grasshouse.dorandoran.post.repository.PostLikeRepository;
import com.grasshouse.dorandoran.post.repository.PostRepository;
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
    private MemberRepository memberRepository;

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
        Long postLikeId = postLikeService.createPostLike(post.getId(), postLiker.getId());
        assertThat(postLikeId).isNotNull();
    }

    @AfterEach
    void tearDown() {
        postLikeRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }
}
