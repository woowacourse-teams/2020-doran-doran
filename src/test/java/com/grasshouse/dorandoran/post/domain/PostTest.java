package com.grasshouse.dorandoran.post.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.fixture.AddressFixture;
import com.grasshouse.dorandoran.fixture.LocationFixture;
import com.grasshouse.dorandoran.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

    @DisplayName("삭제된 댓글을 필터링한다.")
    @Test
    void filterAliveComments() {
        Post post = Post.builder()
            .author(MemberFixture.PERSIST_MEMBER)
            .content("내용")
            .location(LocationFixture.JAMSIL_STATION)
            .authorAddress(AddressFixture.AUTHOR_ADDRESS)
            .address(AddressFixture.ADDRESS)
            .build();

        Comment comment1 = Comment.builder()
            .author(MemberFixture.PERSIST_MEMBER)
            .post(post)
            .content("댓글1")
            .distance(0.0)
            .build();

        Comment comment2 = Comment.builder()
            .author(MemberFixture.PERSIST_MEMBER)
            .post(post)
            .content("댓글2")
            .distance(0.0)
            .build();

        post.addComment(comment1);
        post.addComment(comment2);
        post.removeComment(comment2);

        assertThat(post.filterAliveComments().getComments()).hasSize(1);
    }
}