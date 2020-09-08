package com.grasshouse.dorandoran.member.dto;

import com.grasshouse.dorandoran.member.domain.Member;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

    @NotNull
    private Long id;

    @NotBlank(message = "닉네임은 비어 있을 수 없습니다.")
    private String nickname;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getNickname(), member.getCreatedAt(), member.getUpdatedAt());
    }
}
