package com.grasshouse.dorandoran.post.service.dto;

import com.grasshouse.dorandoran.comment.service.dto.CommentResponse;
import com.grasshouse.dorandoran.member.service.dto.MemberResponse;
import com.grasshouse.dorandoran.post.domain.Address;
import com.grasshouse.dorandoran.post.domain.Location;
import com.grasshouse.dorandoran.post.domain.Post;
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
    private MemberResponse memberResponse;

    @NotNull
    private Address authorAddress;

    @NotBlank(message = "글의 내용은 비어 있을 수 없습니다.")
    private String content;

    @NotNull
    private Location location;

    @NotNull
    private Address address;

    @Builder.Default
    private final List<CommentResponse> comments = new ArrayList<>();
    //TODO : post_like 정보 추후에 추가하기

    public static PostResponse from(Post post) {
        return PostResponse.builder()
            .id(post.getId())
            .memberResponse(MemberResponse.from(post.getAuthor()))
            .authorAddress(post.getAuthorAddress())
            .content(post.getContent())
            .location(post.getLocation())
            .address(post.getAddress())
            .comments(CommentResponse.listFrom(post.getComments()))
            .build();
    }

    public static List<PostResponse> listFrom(List<Post> posts) {
        return posts.stream()
            .map(PostResponse::from)
            .collect(Collectors.toList());
    }
}
