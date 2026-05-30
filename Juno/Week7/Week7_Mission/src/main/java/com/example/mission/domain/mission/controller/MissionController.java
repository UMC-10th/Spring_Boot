package com.example.mission.domain.mission.controller;

import com.example.mission.domain.mission.dto.MissionReqDTO;
import com.example.mission.domain.mission.dto.MissionResDTO;
import com.example.mission.domain.mission.exception.code.MissionSuccessCode;
import com.example.mission.domain.mission.service.MissionService;
import com.example.mission.global.apiPayload.ApiResponse;
import com.example.mission.global.apiPayload.code.BaseSuccessCode;
import com.example.mission.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MissionController {

    private final MissionService missionService;

    // 미션 조회
    @GetMapping("/missions")
    public ApiResponse<MissionResDTO.MissionList> missions(
            @RequestHeader("Authorization") String auth,
            @RequestParam String storeId
    ){
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.missions(auth, storeId));
    }

    // 가게 미션 생성
    @PostMapping("/stores/{storeId}/missions")
    public ApiResponse<Void> createMission(
            @PathVariable Long storeId,
            @RequestBody @Valid MissionReqDTO.CreateMission dto
    ){
        BaseSuccessCode code = MissionSuccessCode.CREATED;
        return ApiResponse.onSuccess(code, missionService.createMission(storeId, dto));
    }

    // 가게 내 미션들 조회
    @GetMapping("/stores/{storeId}/missions")
    public ApiResponse<List<MissionResDTO.GetMission>> getMissions(
            @PathVariable Long storeId,
            @RequestParam Integer pageSize,
            @RequestParam String cursor,
            @RequestParam String query
    ){
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMissions(storeId, pageSize, cursor, query));
    }
}
