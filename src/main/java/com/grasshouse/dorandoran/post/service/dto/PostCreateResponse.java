package com.grasshouse.dorandoran.post.service.dto;

import com.grasshouse.dorandoran.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateResponse {

    private Long id;

    public static PostCreateResponse from(Post post) {
        return new PostCreateResponse(post.getId());
    }
}
