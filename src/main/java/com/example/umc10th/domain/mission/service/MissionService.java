package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MissionService {

    public MissionResDTO.Progress getProgress(Long userId) {
        return MissionResDTO.Progress.builder()
                .completedCount(0)
                .totalCount(0)
                .build();
    }

    public MissionResDTO.MissionList getMissions(Long areaId, Integer page, Integer size) {
        return MissionResDTO.MissionList.builder()
                .missions(Collections.emptyList())
                .totalCount(0L)
                .currentPage(page)
                .build();
    }

    public MissionResDTO.ParticipateResult participate(Long userId, Long missionId) {
        return MissionResDTO.ParticipateResult.builder()
                .participationId(1L)
                .missionStatus("IN_PROGRESS")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public MissionResDTO.CompleteResult complete(Long userId, Long missionId) {
        return MissionResDTO.CompleteResult.builder()
                .participationId(1L)
                .missionStatus("COMPLETED")
                .completedAt(LocalDateTime.now())
                .pointReward(500)
                .build();
    }
}