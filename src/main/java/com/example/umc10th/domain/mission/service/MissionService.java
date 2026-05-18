package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MemberMissionRepository memberMissionRepository;

    // 내 미션 목록 (진행중 / 완료 + 페이징)
    public MissionResDTO.MissionList getMemberMissions(Long memberId, String status, Integer page, Integer size) {
        // status를 Boolean으로 변환
        Boolean isComplete = "COMPLETED".equals(status);

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);

        // @Query 메서드 호출
        Page<MemberMission> missionPage = memberMissionRepository.findByMemberIdAndStatus(memberId, isComplete, pageable);

        return MissionConverter.toMissionList(missionPage);
    }

    // 내가 진행중인 미션만 조회 (오프셋 기반 페이지네이션)
    public MissionResDTO.MissionList getMyInProgressMissions(Long memberId, Integer page, Integer size) {
        // 최신 미션이 먼저 보이도록 id DESC 정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        // isComplete = false -> 진행중인 미션만 조회
        Page<MemberMission> missionPage = memberMissionRepository.findByMemberIdAndStatus(memberId, false, pageable);

        return MissionConverter.toMissionList(missionPage);
    }
}
