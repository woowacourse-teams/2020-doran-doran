package com.grasshouse.dorandoran.comment.service.dto;

import com.grasshouse.dorandoran.post.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {

    private Long memberId;
    private Long postId;
    private String content;
    private Location location;

}
