package com.grasshouse.dorandoran.post.service.dto;

import com.grasshouse.dorandoran.post.domain.PostLike;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeResponse {

    @NotNull
    private Long id;

    @NotNull
    private Long memberId;

    @NotNull
    private Long postId;

    public static PostLikeResponse from(PostLike postLike) {
        return PostLikeResponse.builder()
            .id(postLike.getId())
            .memberId(postLike.getMemberId())
            .postId(postLike.getPost().getId())
            .build();
    }
}
