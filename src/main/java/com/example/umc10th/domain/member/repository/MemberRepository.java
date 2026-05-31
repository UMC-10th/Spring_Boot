package com.example.umc10th.domain.member.repository;

import com.example.umc10th.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdAndDeletedAtIsNull(Long memberId);

    Optional<Member> findByEmailAndDeletedAtIsNull(String email);
    
    // 회원가입 시 이메일 중복 체크용
    boolean existsByEmailAndDeletedAtIsNull(String email);
}
