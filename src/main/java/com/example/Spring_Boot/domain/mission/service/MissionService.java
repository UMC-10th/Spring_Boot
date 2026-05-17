package com.example.Spring_Boot.domain.mission.service;

import com.example.Spring_Boot.domain.mission.converter.MissionConverter;
import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.mission.entity.UserMission;
import com.example.Spring_Boot.domain.mission.enums.Address;
import com.example.Spring_Boot.domain.mission.enums.MissionStatus;
import com.example.Spring_Boot.domain.mission.repository.MissionRepository;
import com.example.Spring_Boot.domain.mission.repository.UserMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;

    public Page<MissionResDTO.MissionItemDTO> getMyMissions(Long userId, String status, int page) {
        MissionStatus missionStatus = MissionStatus.valueOf(status.toUpperCase());
        Pageable pageable = PageRequest.of(page, 10);
        Page<UserMission> userMissions =
                userMissionRepository.findAllByUserIdAndStatus(userId, missionStatus, pageable);
        return userMissions.map(MissionConverter::toMissionItemDTO);
    }

    public Page<MissionResDTO.MissionItemDTO> getAvailableMissions(String address, Long userId, int page) {
        Address addressEnum = Address.valueOf(address.toUpperCase());
        Pageable pageable = PageRequest.of(page, 10);
        return missionRepository.findAvailableMissions(addressEnum, userId, pageable)
                .map(MissionConverter::toHomeMissionItemDTO);
    }
}