package com.grasshouse.dorandoran.member.controller;

import com.grasshouse.dorandoran.config.jwt.LoginMember;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.service.dto.MemberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/my")
    public ResponseEntity<MemberResponse> getProfile(@LoginMember Member member) {
        return ResponseEntity.ok(MemberResponse.from(member));
    }
}
