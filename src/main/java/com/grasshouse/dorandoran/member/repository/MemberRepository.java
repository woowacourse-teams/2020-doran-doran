package com.grasshouse.dorandoran.member.repository;

import com.grasshouse.dorandoran.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByoAuthId(String oAuthId);

    boolean existsMemberByoAuthId(String oAuthId);
}
