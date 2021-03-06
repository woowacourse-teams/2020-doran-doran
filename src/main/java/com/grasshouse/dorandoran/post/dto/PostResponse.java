package com.grasshouse.dorandoran.post.dto;

import com.grasshouse.dorandoran.comment.dto.CommentResponse;
import com.grasshouse.dorandoran.member.dto.MemberResponse;
import com.grasshouse.dorandoran.common.baseentity.EntityStatus;
import com.grasshouse.dorandoran.post.domain.Address;
import com.grasshouse.dorandoran.post.domain.Location;
import com.grasshouse.dorandoran.post.domain.Post;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class PostResponse {

    @NotNull
    private Long id;

    @NotNull
    private MemberResponse author;

    @NotNull
    private Address authorAddress;

    @NotBlank(message = "글의 내용은 비어 있을 수 없습니다.")
    private String content;

    @NotNull
    private Location location;

    @NotNull
    private Address address;

    @NotNull
    private LocalDateTime createdAt;

    @Builder.Default
    private final List<CommentResponse> comments = new ArrayList<>();

    @NotNull
    private List<PostLikeResponse> likes;

    private EntityStatus entityStatus;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
            .id(post.getId())
            .author(MemberResponse.from(post.getAuthor()))
            .authorAddress(post.getAuthorAddress())
            .content(post.getContent())
            .location(post.getLocation())
            .address(post.getAddress())
            .createdAt(post.getCreatedAt())
            .comments(CommentResponse.listFrom(post.getComments()))
            .likes(PostLikeResponse.listFrom(post.getLikes()))
            .entityStatus(post.getStatus())
            .build();
    }

    public static List<PostResponse> listFrom(List<Post> posts) {
        return posts.stream()
            .map(PostResponse::from)
            .collect(Collectors.toList());
    }
}
