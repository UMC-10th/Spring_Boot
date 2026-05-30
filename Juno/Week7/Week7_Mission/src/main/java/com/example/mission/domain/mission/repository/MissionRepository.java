package com.example.mission.domain.mission.repository;

import com.example.mission.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

	Page<Mission> findAllByStore_Id(Long storeId, PageRequest pageRequest);
}
