package com.example.Spring_Boot.domain.mission.controller;

import com.example.Spring_Boot.domain.mission.dto.MissionReqDTO;
import com.example.Spring_Boot.domain.mission.dto.MissionResDTO;
import com.example.Spring_Boot.domain.mission.service.MissionService;
import com.example.Spring_Boot.global.apiPayload.ApiResponse;
import com.example.Spring_Boot.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    // 미션 1: 내가 진행 중인 미션 조회 (오프셋 기반)
    // GET /api/missions/my?userId=1&status=IN_PROGRESS&pageSize=10&pageNumber=0
    @GetMapping("/my")
    public ApiResponse<MissionResDTO.OffsetPagination<MissionResDTO.GetMission>> getMyMissions(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "IN_PROGRESS") String status,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "0") Integer pageNumber
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,
                missionService.getMyMissions(userId, status, pageSize, pageNumber));
    }

    // 기존: 지역별 도전 가능 미션
    @GetMapping
    public ApiResponse<Page<MissionResDTO.GetMission>> getAvailableMissions(
            @RequestParam String address,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,
                missionService.getAvailableMissions(address, userId, page));
    }

    // 기존: 미션 상태 변경
    @PatchMapping("/{missionId}/status")
    public ApiResponse<Void> updateMissionStatus(
            @PathVariable Long missionId,
            @RequestBody MissionReqDTO.UpdateStatusDTO request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK);
    }
}