package com.example.mission.domain.mission.repository;

import com.example.mission.domain.mission.entity.Mission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    Slice<Mission> findMissionsByStore_IdAndIdLessThanOrderByIdDesc(Long storeId, Long idCursor, Pageable pageable);
    Slice<Mission> findMissionsByStore_IdOrderByIdDesc(Long storeId, Pageable pageable);
}
