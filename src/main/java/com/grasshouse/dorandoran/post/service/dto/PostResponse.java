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
    //TODO : comment, post_like 정보 추후에 추가하기

    public static PostResponse from(Post post) {
        return PostResponse.builder()
            .id(post.getId())
            .memberResponse(MemberResponse.from(post.getAuthor()))
            .content(post.getContent())
            .location(post.getLocation())
            .address(post.getAddress())
            .build();
    }

    public static List<PostResponse> listFrom(List<Post> posts) {
        return posts.stream()
            .map(PostResponse::from)
            .collect(Collectors.toList());
    }
}
