package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public MissionResDTO.CreateMissionResultDTO createMission(
            MissionReqDTO.CreateMissionDTO request
    ) {
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow();

        Mission mission = MissionConverter.toMission(request, store);
        Mission savedMission = missionRepository.save(mission);

        return MissionConverter.toCreateMissionResultDTO(savedMission);
    }

    public MissionResDTO.MissionInfoDTO getMission(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow();

        return MissionConverter.toMissionInfoDTO(mission);
    }

    public MissionResDTO.HomeMissionListDTO getHomeMissions(
            Long locationId,
            Integer page
    ) {
        Page<Mission> missionPage = missionRepository.findMissionsByLocation(
                locationId,
                PageRequest.of(page, 10)
        );

        List<MissionResDTO.HomeMissionDTO> missionList = missionPage.stream()
                .map(mission -> MissionResDTO.HomeMissionDTO.builder()
                        .missionId(mission.getId())
                        .storeName(mission.getStore().getName())
                        .missionTitle(mission.getTitle())
                        .missionDescription(mission.getDescription())
                        .build())
                .toList();

        return MissionResDTO.HomeMissionListDTO.builder()
                .missionList(missionList)
                .listSize(missionList.size())
                .totalPage(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .isFirst(missionPage.isFirst())
                .isLast(missionPage.isLast())
                .build();
    }
}