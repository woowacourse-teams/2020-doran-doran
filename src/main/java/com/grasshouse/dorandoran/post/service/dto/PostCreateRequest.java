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

    private Member author;
    private String content;
    private Location location;

    public Post toPost() {
        return Post.builder()
            .author(author)
            .content(content)
            .location(location)
            .build();
    }
}
