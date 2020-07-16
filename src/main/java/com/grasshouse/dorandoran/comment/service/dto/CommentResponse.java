package com.grasshouse.dorandoran.comment.service.dto;

import com.grasshouse.dorandoran.comment.domain.Comment;
import com.grasshouse.dorandoran.member.service.dto.MemberResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private MemberResponse author;
    private Long postId;
    private String content;
    private Double distance;
    //TODO : comment_like 정보 추후에 추가하기

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
            .id(comment.getId())
            .author(MemberResponse.from(comment.getAuthor()))
            .postId(comment.getPost().getId())
            .content(comment.getContent())
            .distance(comment.getDistance())
            .build();
    }

    public static List<CommentResponse> listFrom(List<Comment> comments) {
        return comments.stream()
            .map(CommentResponse::from)
            .collect(Collectors.toList());
    }
}