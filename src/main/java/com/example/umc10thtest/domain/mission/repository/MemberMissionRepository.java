package com.example.umc10thtest.domain.mission.repository;

import com.example.umc10thtest.domain.member.entity.Member;
import com.example.umc10thtest.domain.mission.entity.mapping.MemberMission;
import com.example.umc10thtest.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    Page<MemberMission> findByMemberAndStatus(Member member, MissionStatus status, Pageable pageable);
}
