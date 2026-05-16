package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    Page<MemberMission> findByMember_Id(Long memberId, Pageable pageable);
    Page<MemberMission> findByMember_IdAndIsComplete(Long memberId, Boolean isComplete, Pageable pageable);
}
