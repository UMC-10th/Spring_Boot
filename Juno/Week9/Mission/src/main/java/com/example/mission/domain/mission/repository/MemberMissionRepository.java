package com.example.mission.domain.mission.repository;

import com.example.mission.domain.mission.entity.mapping.MemberMission;
import com.example.mission.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    Slice<MemberMission> findByMember_IdAndStatusAndIdLessThanOrderByIdDesc(Long memberId, MissionStatus status, Long idCursor, Pageable pageable);
    Slice<MemberMission> findByMember_IdAndStatusOrderByIdDesc(Long memberId, MissionStatus status, Pageable pageable);
}
