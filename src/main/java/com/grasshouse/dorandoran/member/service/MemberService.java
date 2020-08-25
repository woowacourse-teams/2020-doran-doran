package com.grasshouse.dorandoran.member.service;

import com.grasshouse.dorandoran.common.exception.MemberNotFoundException;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import com.grasshouse.dorandoran.member.service.dto.MemberResponse;
import com.grasshouse.dorandoran.member.service.dto.MemberUpdateRequest;
import com.grasshouse.dorandoran.member.service.dto.MemberUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberUpdateResponse update(Member member, MemberUpdateRequest request) {
        Member persistMember = memberRepository.findById(member.getId())
            .orElseThrow(MemberNotFoundException::new);

        Member updatedMember = persistMember.update(request.getNickname());
        memberRepository.save(updatedMember);

        return MemberUpdateResponse.from(updatedMember);
    }
}
