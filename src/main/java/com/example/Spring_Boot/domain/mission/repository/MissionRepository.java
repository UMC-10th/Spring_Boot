package com.example.Spring_Boot.domain.mission.repository;

import com.example.Spring_Boot.domain.mission.entity.Mission;
import com.example.Spring_Boot.domain.mission.enums.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("SELECT m FROM Mission m " +
            "JOIN FETCH m.store s " +
            "WHERE s.address = :address " +
            "AND m.id NOT IN (" +
            "    SELECT um.mission.id FROM UserMission um WHERE um.user.id = :userId" +
            ")")
    Page<Mission> findAvailableMissions(
            @Param("address") Address address,
            @Param("userId") Long userId,
            Pageable pageable);
}