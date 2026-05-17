package com.example.Spring_Boot.domain.mission.service;

import com.example.Spring_Boot.domain.mission.converter.MissionConverter;
import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.mission.entity.Mission;
import com.example.Spring_Boot.domain.mission.entity.UserMission;
import com.example.Spring_Boot.domain.mission.enums.Address;
import com.example.Spring_Boot.domain.mission.enums.MissionStatus;
import com.example.Spring_Boot.domain.mission.exception.MissionException;
import com.example.Spring_Boot.domain.mission.exception.code.MissionErrorCode;
import com.example.Spring_Boot.domain.mission.repository.MissionRepository;
import com.example.Spring_Boot.domain.mission.repository.UserMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;

    // 미션 1: 내가 진행 중인 미션 조회 (오프셋 기반)
    public MissionResDTO.OffsetPagination<MissionResDTO.GetMission> getMyMissions(
            Long userId, String status, Integer pageSize, Integer pageNumber) {

        MissionStatus missionStatus = MissionStatus.valueOf(status.toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Page<UserMission> userMissions =
                userMissionRepository.findAllByUserIdAndStatus(userId, missionStatus, pageRequest);

        List<MissionResDTO.GetMission> data = userMissions
                .map(MissionConverter::toMissionItemDTO)
                .toList();

        return MissionConverter.toOffsetPagination(data, userMissions.getNumber(), userMissions.getSize());
    }

    // 기존: 지역별 도전 가능 미션
    public Page<MissionResDTO.GetMission> getAvailableMissions(String address, Long userId, int page) {
        Address addressEnum = Address.valueOf(address.toUpperCase());
        Pageable pageable = PageRequest.of(page, 10);
        return missionRepository.findAvailableMissions(addressEnum, userId, pageable)
                .map(MissionConverter::toHomeMissionItemDTO);
    }
}