package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRespository extends JpaRepository<Mission, Long> {

    @EntityGraph(attributePaths = "store")
    Page<Mission> findByStore_Location_Id(Long locationId, Pageable pageable);

    List<Mission> findAllByStore_Id(Long storeId);

    Page<Mission> findAllByStore_Id(Long storeId, Pageable pageable);

    Slice<Mission> findMissionByStore_IdAndIdLessThanOrderByIdDesc(Long storeId, long idCursor, PageRequest pageRequest);

    Slice<Mission> findMissionByStore_IdOrderByIdDesc(Long storeId, PageRequest pageRequest);
}
