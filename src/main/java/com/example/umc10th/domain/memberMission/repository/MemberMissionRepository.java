package com.example.umc10th.domain.memberMission.repository;

import com.example.umc10th.domain.memberMission.entity.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    @Query("""
            select mm
            from MemberMission mm
            join fetch mm.mission m
            join fetch m.store s
            where mm.member.id = :memberId
            and mm.isComplete = :isComplete
            """)
    Page<MemberMission> findMyMissions(
            @Param("memberId") Long memberId,
            @Param("isComplete") Boolean isComplete,
            Pageable pageable
    );
}