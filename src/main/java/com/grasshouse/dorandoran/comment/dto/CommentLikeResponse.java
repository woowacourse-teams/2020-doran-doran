package com.grasshouse.dorandoran.comment.dto;

import com.grasshouse.dorandoran.comment.domain.CommentLike;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentLikeResponse {

    @NotNull
    private Long id;

    @NotNull
    private Long memberId;

    @NotNull
    private Long commentId;

    public static CommentLikeResponse from(CommentLike commentLike) {
        return CommentLikeResponse.builder()
            .id(commentLike.getId())
            .memberId(commentLike.getMemberId())
            .commentId(commentLike.getComment().getId())
            .build();
    }

    public static List<CommentLikeResponse> listFrom(Set<CommentLike> commentLikes) {
        return commentLikes.stream()
            .map(CommentLikeResponse::from)
            .collect(Collectors.toList());
    }
}