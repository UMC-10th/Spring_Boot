package com.umc.umc10th.domain.mission.service;


import com.umc.umc10th.domain.mission.dto.response.MissionResponseDto;
import com.umc.umc10th.domain.mission.entity.Mission;
import com.umc.umc10th.domain.mission.repository.MissionRepository;
import com.umc.umc10th.domain.user.repository.UserDoingMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserDoingMissionRepository userDoingMissionRepository;

    // 임시 인증 유저 ID - 실제로는 SecurityContextHolder 등에서 추출
    private static final Long TEMP_USER_ID = 1L;
    private static final int PAGE_SIZE = 10;

    public MissionResponseDto.GetMissions getMissions(Long locationId) {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<Mission> page = missionRepository.findAvailableMissions(locationId, TEMP_USER_ID, pageable);

        List<MissionResponseDto.GetMissions.GetMission> items = page.getContent().stream()
                .map(m -> MissionResponseDto.GetMissions.GetMission.builder()
                        .title(m.getTitle())
                        .content(m.getContent())
                        .reward(m.getReward())
                        .build())
                .toList();

        return MissionResponseDto.GetMissions.builder()
                .missions(items)
                .build();
    }
}