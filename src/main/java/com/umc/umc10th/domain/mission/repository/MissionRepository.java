package com.umc.umc10th.domain.mission.repository;

import com.umc.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("""
            SELECT m FROM Mission m
            WHERE m.location.id = :locationId
              AND m.finishAt > CURRENT_TIMESTAMP
              AND NOT EXISTS (
                  SELECT udm FROM UserDoingMission udm
                  WHERE udm.mission = m AND udm.user.id = :userId
              )
            """)
    Page<Mission> findAvailableMissions(
            @Param("locationId") Long locationId,
            @Param("userId") Long userId,
            Pageable pageable
    );
}