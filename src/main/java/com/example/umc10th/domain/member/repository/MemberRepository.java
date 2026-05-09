package com.example.umc10th.domain.member.repository;

import com.example.umc10th.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    @Query("""
        select count(r)
        from Review r
        where r.member.id = :memberId
        """)
    Long countReviewByMemberId(@Param("memberId") Long memberId);
}