package com.example.umc10thtest.domain.mission.service;

import com.example.umc10thtest.domain.mission.entity.Mission;
import com.example.umc10thtest.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;

    public Page<Mission> getAvailableMissions(Long locationId, Long memberId, Integer page) {
        return missionRepository.findAvailableMissions(locationId, memberId, PageRequest.of(page, 10));
    }
}
