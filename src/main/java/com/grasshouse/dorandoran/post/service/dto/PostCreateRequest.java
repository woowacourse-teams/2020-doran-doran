package com.grasshouse.dorandoran.post.service.dto;

import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.post.domain.Address;
import com.grasshouse.dorandoran.post.domain.Location;
import com.grasshouse.dorandoran.post.domain.Post;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @NotNull
    private Long memberId;

    private Address authorAddress;

    @Length(max = 200)
    @NotBlank(message = "글의 내용은 비어 있을 수 없습니다.")
    private String content;

    @NotNull
    private Location location;

    public Post toPost(Member member) {
        return Post.builder()
            .author(member)
            .authorAddress(authorAddress)
            .content(content)
            .location(location)
            .build();
    }
}
