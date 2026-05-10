package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRespository extends JpaRepository<Mission, Long> {

    Page<Mission> findByStore_Location_Id(Integer locationId, Pageable pageable);
}
