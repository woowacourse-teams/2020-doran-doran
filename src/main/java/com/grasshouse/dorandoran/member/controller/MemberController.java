package com.grasshouse.dorandoran.member.controller;

import com.grasshouse.dorandoran.config.jwt.LoginMember;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.service.MemberService;
import com.grasshouse.dorandoran.member.dto.MemberResponse;
import com.grasshouse.dorandoran.member.dto.MemberUpdateRequest;
import com.grasshouse.dorandoran.member.dto.MemberUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/me")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberResponse> getProfile(@LoginMember Member member) {
        return ResponseEntity.ok(MemberResponse.from(member));
    }

    @PutMapping
    public ResponseEntity<MemberUpdateResponse> updateMember(@LoginMember Member member,
        @RequestBody MemberUpdateRequest request) {
        MemberUpdateResponse response = memberService.update(member, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@LoginMember Member member) {
        memberService.delete(member);
        return ResponseEntity.noContent().build();
    }
}
