package com.grasshouse.dorandoran.post.service.dto;

import com.grasshouse.dorandoran.post.domain.Post;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateResponse {

    @NotNull
    private Long id;

    public static PostCreateResponse from(Post post) {
        return new PostCreateResponse(post.getId());
    }
}
