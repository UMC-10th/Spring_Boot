package com.umc.umc10th.domain.user.repository;


import com.umc.umc10th.domain.user.entity.UserDoingMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDoingMissionRepository extends JpaRepository<UserDoingMission, Long> {

    @Query("""
            SELECT udm FROM UserDoingMission udm
            JOIN FETCH udm.mission m
            JOIN FETCH m.store s
            WHERE udm.user.id = :userId
            """)
    Page<UserDoingMission> findMyMissions(
            @Param("userId") Long userId,
            @Param("status") String status,
            Pageable pageable
    );

    Page<UserDoingMission> findAllByUser_IdAndMission_Location_Id(
            Long userId,
            Long locationId,
            Pageable pageable
    );
}