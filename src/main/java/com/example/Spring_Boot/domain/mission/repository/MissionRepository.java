package com.example.Spring_Boot.domain.mission.repository;

import com.example.Spring_Boot.domain.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
