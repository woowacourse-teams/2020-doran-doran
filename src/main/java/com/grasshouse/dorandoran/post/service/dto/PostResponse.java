package com.grasshouse.dorandoran.post.service.dto;

import com.grasshouse.dorandoran.member.service.dto.MemberResponse;
import com.grasshouse.dorandoran.post.domain.Address;
import com.grasshouse.dorandoran.post.domain.Location;
import com.grasshouse.dorandoran.post.domain.Post;
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
public class PostResponse {

    private Long id;
    private MemberResponse memberResponse;
    private String content;
    private Location location;
    private Address address;

    public static PostResponse of(Post post) {
        return PostResponse.builder()
            .id(post.getId())
            .memberResponse(MemberResponse.of(post.getAuthor()))
            .content(post.getContent())
            .location(post.getLocation())
            .address(post.getAddress())
            .build();
    }

    public static List<PostResponse> listOf(List<Post> posts) {
        return posts.stream()
            .map(PostResponse::of)
            .collect(Collectors.toList());
    }
}
