package com.grasshouse.dorandoran.comment.service.dto;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.member.service.dto.MemberResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    @NotNull
    private Long id;

    @NotNull
    private MemberResponse author;

    @NotNull
    private Long postId;

    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;

    @NotNull
    private Double distance;

    @NotNull
    private LocalDateTime createdAt;

    private List<CommentLikeResponse> likes;

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
            .id(comment.getId())
            .author(MemberResponse.from(comment.getAuthor()))
            .postId(comment.getPost().getId())
            .content(comment.getContent())
            .distance(comment.getDistance())
            .createdAt(comment.getCreatedAt())
            .likes(comment.getLikes()
                .stream()
                .map(CommentLikeResponse::from)
                .collect(Collectors.toList())
            )
            .build();
    }

    public static List<CommentResponse> listFrom(List<Comment> comments) {
        return comments.stream()
            .map(CommentResponse::from)
            .collect(Collectors.toList());
    }
}
