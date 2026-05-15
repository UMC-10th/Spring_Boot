package com.example.umc10th.domain.mission.repository;
import com.example.umc10th.domain.mission.entity.MissionParticipation;
import com.example.umc10th.domain.mission.entity.enums.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface MissionParticipationRepository extends JpaRepository<MissionParticipation, Long> {

    @Query("""
            SELECT COUNT(mp)
            FROM MissionParticipation mp
            WHERE mp.member.id = :memberId
            """)
    long countByMemberId(@Param("memberId") Long memberId);

    @Query("""
            SELECT COUNT(mp)
            FROM MissionParticipation mp
            WHERE mp.member.id = :memberId
            AND mp.missionStatus = :status
            """)
    long countByMemberIdAndMissionStatus(
            @Param("memberId") Long memberId,
            @Param("status") MissionStatus status
    );

    @Query("""
            SELECT CASE WHEN COUNT(mp) > 0 THEN TRUE ELSE FALSE END
            FROM MissionParticipation mp
            WHERE mp.member.id = :memberId
            AND mp.mission.missionId = :missionId
            """)
    boolean existsByMemberIdAndMissionId(
            @Param("memberId") Long memberId,
            @Param("missionId") Long missionId
    );

    @Query("""
            SELECT mp
            FROM MissionParticipation mp
            JOIN FETCH mp.mission
            WHERE mp.member.id = :memberId
            AND mp.mission.missionId = :missionId
            """)
    Optional<MissionParticipation> findByMemberIdAndMissionId(
            @Param("memberId") Long memberId,
            @Param("missionId") Long missionId
    );

    @Query(
            value = """
                    SELECT mp
                    FROM MissionParticipation mp
                    JOIN FETCH mp.mission m
                    WHERE mp.member.id = :memberId
                    AND mp.missionStatus = :status
                    """,
            countQuery = """
                    SELECT COUNT(mp)
                    FROM MissionParticipation mp
                    WHERE mp.member.id = :memberId
                    AND mp.missionStatus = :status
                    """
    )
    Page<MissionParticipation> findByMemberIdAndStatusWithFetch(
            @Param("memberId") Long memberId,
            @Param("status") MissionStatus status,
            Pageable pageable
    );
}
