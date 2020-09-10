package com.grasshouse.dorandoran.post.dto;

import com.grasshouse.dorandoran.post.domain.PostLike;
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

    public static List<PostLikeResponse> listFrom(Set<PostLike> postLikes) {
        return postLikes.stream()
            .map(PostLikeResponse::from)
            .collect(Collectors.toList());
    }
}
