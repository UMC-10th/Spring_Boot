package com.example.umc10thtest.domain.store.service;

import com.example.umc10thtest.domain.mission.dto.MissionReqDTO;
import com.example.umc10thtest.domain.mission.entity.Mission;
import com.example.umc10thtest.domain.mission.repository.MissionRepository;
import com.example.umc10thtest.domain.store.entity.Store;
import com.example.umc10thtest.domain.store.repository.StoreRepository;
import com.example.umc10thtest.global.apiPayload.code.GeneralErrorCode;
import com.example.umc10thtest.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;

    public Mission createMission(Long storeId, MissionReqDTO.CreateMissionReq request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));

        Mission mission = Mission.builder()
                .missionSpec(request.getConditional())
                .reward(request.getPoint())
                .deadline(request.getDeadline())
                .store(store)
                .build();

        return missionRepository.save(mission);
    }

    @Transactional(readOnly = true)
    public List<Mission> getStoreMissions(Long storeId) {
        storeRepository.findById(storeId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));
        return missionRepository.findByStoreId(storeId);
    }
}
