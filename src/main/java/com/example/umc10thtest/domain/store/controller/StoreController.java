package com.example.umc10thtest.domain.store.controller;

import com.example.umc10thtest.domain.mission.converter.MissionConverter;
import com.example.umc10thtest.domain.mission.dto.MissionReqDTO;
import com.example.umc10thtest.domain.mission.dto.MissinoResDTO;
import com.example.umc10thtest.domain.mission.entity.Mission;
import com.example.umc10thtest.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10thtest.domain.store.service.StoreService;
import com.example.umc10thtest.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
@Tag(name = "Store", description = "가게 관련 API")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/{storeId}/missions")
    @Operation(summary = "가게 미션 생성", description = "특정 가게에 미션을 생성합니다.")
    public ApiResponse<Void> createMission(
            @PathVariable Long storeId,
            @RequestBody @Valid MissionReqDTO.CreateMissionReq request
    ) {
        storeService.createMission(storeId, request);
        return ApiResponse.onSuccess(MissionSuccessCode.CREATE_MISSION);
    }

    @GetMapping("/{storeId}/missions")
    @Operation(summary = "가게 미션 목록 조회", description = "특정 가게의 미션 목록을 조회합니다.")
    public ApiResponse<MissinoResDTO.StoreMissionListRes> getStoreMissions(
            @PathVariable Long storeId
    ) {
        List<Mission> missions = storeService.getStoreMissions(storeId);
        return ApiResponse.onSuccess(MissionSuccessCode.GET_STORE_MISSIONS, MissionConverter.toStoreMissionListRes(missions));
    }
}
