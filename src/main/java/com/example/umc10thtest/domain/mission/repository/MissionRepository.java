package com.example.umc10thtest.domain.mission.repository;

import com.example.umc10thtest.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("SELECT m FROM Mission m WHERE m.store.location.id = :locationId " +
           "AND m.id NOT IN (SELECT mm.mission.id FROM MemberMission mm WHERE mm.member.id = :memberId)")
    Page<Mission> findAvailableMissions(@Param("locationId") Long locationId,
                                        @Param("memberId") Long memberId,
                                        Pageable pageable);

    List<Mission> findByStoreId(Long storeId);
}
