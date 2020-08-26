package com.grasshouse.dorandoran.member.service.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateRequest {
    @NotBlank(message = "변경할 닉네임 값은 비어있을 수 없습니다.")
    private String nickname;
}
