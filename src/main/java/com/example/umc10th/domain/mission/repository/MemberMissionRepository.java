package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 내 미션 (진행중 / 완료 상태별 페이징) - @Query 사용
    @Query("SELECT mm FROM MemberMission mm " +
            "WHERE mm.member.id = :memberId " +
            "AND mm.isComplete = :isComplete")
    Page<MemberMission> findMemberIdAndStatus(
            @Param("memberId") Long memberId,
            @Param("isComplete") Boolean isComplete,
            Pageable pageable
    );

    // 홈 화면용: 내가 받은 모든 미션 (status별 카운트용)
    @Query("SELECT mm FROM MemberMission mm WHERE mm.member.id = :memberId")
    List<MemberMission> findAllByMemberId(@Param("memberId") Long memberId);

    // 카운트 쿼리들 (홈 화면 미션 요약용)
    @Query("SELECT COUNT(mm) FROM MemberMission mm WHERE mm.member.id = :memberId")
    Long countByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT COUNT(mm) FROM MemberMission mm " +
            "WHERE mm.member.id = :memberId AND mm.isComplete = :isComplete")
    Long countByMemberIdAndStatus(
            @Param("memberId") Long memberId,
            @Param("isComplete") Boolean isComplete
    );
}
