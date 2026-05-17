package com.example.Spring_Boot.domain.mission.repository;

import com.example.Spring_Boot.domain.mission.entity.UserMission;
import com.example.Spring_Boot.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    @Query("SELECT um FROM UserMission um " +
            "JOIN FETCH um.mission m " +
            "JOIN FETCH m.store s " +
            "WHERE um.user.id = :userId AND um.status = :status")
    Page<UserMission> findAllByUserIdAndStatus(
            @Param("userId") Long userId,
            @Param("status") MissionStatus status,
            Pageable pageable);
}