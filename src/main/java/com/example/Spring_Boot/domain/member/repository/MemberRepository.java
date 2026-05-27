package com.example.Spring_Boot.domain.member.repository;

import com.example.Spring_Boot.domain.member.entity.Member;
import com.example.Spring_Boot.domain.member.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findBySocialProviderAndSocialUid(SocialType socialProvider, String socialUid);

    boolean existsByEmail(String email);
}
