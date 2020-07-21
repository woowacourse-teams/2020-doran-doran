package com.grasshouse.dorandoran.post.service.dto;

import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.post.domain.Location;
import com.grasshouse.dorandoran.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    private Long memberId;
    private String content;
    private Location location;
    //TODO: Address 추가하기

    public Post toPost(Member member) {
        return Post.builder()
            .author(member)
            .content(content)
            .location(location)
            .build();
    }
}
