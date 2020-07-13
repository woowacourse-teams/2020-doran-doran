package com.grasshouse.dorandoran.member.service.dto;

import com.grasshouse.dorandoran.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

    private Long id;
    private String nickname;

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getNickname());
    }
}
