package com.example.umc10th.domain.member.repository;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository: save(), findById(), findAll(), delete() 등 기본 CRUD 기능 자동 제공
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Member> findBySocialTypeAndSocialUid(SocialType socialType, String socialUid);
}
