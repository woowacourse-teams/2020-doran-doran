package com.grasshouse.dorandoran.post.service.dto;

import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.post.domain.Location;
import com.grasshouse.dorandoran.post.domain.Post;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private String authorAddress;

    @NotBlank(message = "글의 내용은 비어 있을 수 없습니다.")
    private String content;

    @NotNull
    private Location location;
    //TODO: Address 추가하기

    public Post toPost(Member member) {
        return Post.builder()
            .author(member)
            .authorAddress(authorAddress)
            .content(content)
            .location(location)
            .build();
    }
}
