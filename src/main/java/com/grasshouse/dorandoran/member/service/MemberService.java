package com.grasshouse.dorandoran.member.service;

import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
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
        Member updatedMember = member.update(request.getNickname());
        memberRepository.save(updatedMember);

        return MemberUpdateResponse.from(updatedMember);
    }

    @Transactional
    public void delete(Member member) {
        memberRepository.delete(member);
    }
}
