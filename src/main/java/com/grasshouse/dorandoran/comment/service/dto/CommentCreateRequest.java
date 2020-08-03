package com.grasshouse.dorandoran.comment.service.dto;

import com.grasshouse.dorandoran.post.domain.Location;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long postId;

    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;

    @NotNull
    private Location location;

}
