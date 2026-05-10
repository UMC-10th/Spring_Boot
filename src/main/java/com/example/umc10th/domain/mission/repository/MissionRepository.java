package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query(
            value = """
            SELECT m
            FROM Mission m
            JOIN FETCH m.restaurant r
            JOIN FETCH r.area a
            WHERE a.id = :areaId
              AND m.deadline > :now
              AND m.missionId NOT IN (
                  SELECT mp.mission.missionId
                  FROM MissionParticipation mp
                  WHERE mp.member.memberId = :memberId
              )
            """,
            countQuery = """
            SELECT COUNT(m)
            FROM Mission m
            WHERE m.restaurant.area.id = :areaId
              AND m.deadline > :now
              AND m.missionId NOT IN (
                  SELECT mp.mission.missionId
                  FROM MissionParticipation mp
                  WHERE mp.member.memberId = :memberId
              )
            """
    )
    Page<Mission> findChallengeableByAreaId(
            @Param("memberId") Long memberId,
            @Param("areaId") Long areaId,
            @Param("now") LocalDateTime now,
            Pageable pageable
    );
}