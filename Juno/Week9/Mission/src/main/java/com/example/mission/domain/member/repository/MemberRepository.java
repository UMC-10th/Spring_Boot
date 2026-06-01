package com.example.mission.domain.member.repository;

import com.example.mission.domain.member.entity.Member;
import com.example.mission.domain.member.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findBySocialTypeAndSocialUid(SocialType socialType, String socialUid);
    Optional<Member> findBySocialUid(String socialUid);
}
