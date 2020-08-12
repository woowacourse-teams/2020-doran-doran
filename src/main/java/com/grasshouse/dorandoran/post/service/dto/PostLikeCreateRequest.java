package com.grasshouse.dorandoran.post.service.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeCreateRequest {

    @NotNull
    Long postId;

    @NotNull
    Long memberId;
}
