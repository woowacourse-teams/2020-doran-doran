package com.grasshouse.dorandoran.comment.service.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeCreateRequest {

    @NotNull
    Long commentId;

    @NotNull
    Long memberId;
}
