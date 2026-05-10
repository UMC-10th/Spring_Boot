package com.example.week6_mission.domain.mission.repository;

import com.example.week6_mission.domain.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

	List<Mission> findAllByStore_Id(Long storeId);
}
