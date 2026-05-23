package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    @EntityGraph(attributePaths = {"mission", "mission.store"})
    Page<MemberMission> findByMember_Id(Long memberId, Pageable pageable);

    @EntityGraph(attributePaths = {"mission", "mission.store"})
    Page<MemberMission> findByMember_IdAndIsComplete(Long memberId, Boolean isComplete, Pageable pageable);

    Slice<MemberMission> findByMember_IdAndIsCompleteOrderByIdDesc(
            Long storeId,  Boolean isComplete, PageRequest pageRequest
    );

    Slice<MemberMission> findByMember_IdAndIsCompleteAndIdLessThanOrderByIdDesc(
            Long memberId,
            Boolean isComplete,
            Long idCursor,
            Pageable pageable
    );
}
