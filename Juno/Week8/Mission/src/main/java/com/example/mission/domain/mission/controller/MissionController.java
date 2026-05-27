package com.example.mission.domain.mission.controller;

import com.example.mission.domain.mission.dto.MissionReqDTO;
import com.example.mission.domain.mission.dto.MissionResDTO;
import com.example.mission.domain.mission.exception.code.MissionSuccessCode;
import com.example.mission.domain.mission.service.MissionService;
import com.example.mission.global.apiPayload.ApiResponse;
import com.example.mission.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MissionController {

    private final MissionService missionService;

    // 가게 미션 생성
    @PostMapping("/v1/stores/{storeId}/missions")
    public ApiResponse<Void> createMission(
            @PathVariable Long storeId,
            @RequestBody @Valid MissionReqDTO.CreateMission dto
    ) {
        BaseSuccessCode code = MissionSuccessCode.CREATED;
        return ApiResponse.onSuccess(code, missionService.createMission(storeId, dto));
    }

    // 가게 내 미션들 조회
    @GetMapping("/v1/stores/{storeId}/missions")
    public ApiResponse<MissionResDTO.Pagination<MissionResDTO.GetMission>> getMissions(
            @PathVariable Long storeId,
            @RequestParam Integer pageSize,
            @RequestParam String cursor,
            @RequestParam String query
    ) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMissions(storeId, pageSize, cursor, query));
    }

    // 내가 진행 중인 미션 조회
    @GetMapping("/v1/members/{memberId}/missions/ongoing")
    public ApiResponse<MissionResDTO.Pagination<MissionResDTO.GetMemberMission>> getMyOngoingMissions(
            @PathVariable Long memberId,
            @RequestParam Integer pageSize,
            @RequestParam String cursor
    ) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMyOngoingMissions(memberId, pageSize, cursor));
    }
}
