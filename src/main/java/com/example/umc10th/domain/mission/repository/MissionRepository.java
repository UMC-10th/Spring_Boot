package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    // 홈 화면: 특정 지역에서 도전 가능한 미션 목록 (페이징)
    @EntityGraph(attributePaths = {"store", "store.location"})
    Page<Mission> findByStore_Location_Id(Long locationId, Pageable pageable);

    // 가게 내 미션 목록 조회
    Page<Mission> findByStoreId(Long storeId, Pageable pageable);
}