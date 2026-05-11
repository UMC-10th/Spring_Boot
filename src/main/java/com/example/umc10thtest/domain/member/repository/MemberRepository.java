package com.example.umc10thtest.domain.member.repository;

import com.example.umc10thtest.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
