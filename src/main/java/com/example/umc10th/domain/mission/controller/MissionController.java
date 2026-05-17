package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MissionController {

    private final MissionService missionService;

    // 홈 화면
    // TODO: Security 적용 후 매개변수 변경
    @GetMapping("/home")
    public ApiResponse<MissionResDTO.GetHome> getHome(
            @RequestParam Long memberId,
            @RequestParam Long locationId,
            Pageable pageable
    ) {
        MissionReqDTO.GetHome req = new MissionReqDTO.GetHome(memberId, locationId);
        BaseSuccessCode code = MissionSuccessCode.HOME_OK;
        return ApiResponse.success(code, missionService.getHome(req, pageable));
    }

    // 내 미션 목록 조회
    // TODO: Security 적용 후 매개변수 변경
    @PostMapping("/my/missions")
    public ApiResponse<MissionResDTO.Pagination<MissionResDTO.GetMission>> getMyMissions(
            @RequestBody MissionReqDTO.GetMyMissions req,
            @RequestParam Integer pageSize,
            @RequestParam String cursor,
            @RequestParam String query
    ) {
        BaseSuccessCode code = MissionSuccessCode.MY_MISSIONS_OK;
        return ApiResponse.success(code, missionService.getMyMissions(req.memberId(), pageSize, cursor, query));
    }

    //  미션 완료 처리
    // PATCH /api/v1/my/missions/{missionId}/complete
    @PatchMapping("/my/missions/{missionId}/complete")
    public ApiResponse<MissionResDTO.CompleteMission> completeMission(
            @PathVariable Long missionId
    ) {
        BaseSuccessCode code = MissionSuccessCode.MISSION_COMPLETE_OK;
        return ApiResponse.success(code, missionService.completeMission(missionId));
    }

    // 가게 미션 생성
    @PostMapping("/stores/{storeId}/missions")
    public ApiResponse<Void> craeteMission(
            @PathVariable Long storeId,
            @RequestBody @Valid MissionReqDTO.CreateMission dto
    ){
        BaseSuccessCode code = MissionSuccessCode.CREATED;
        return ApiResponse.success(code, missionService.createMission(storeId, dto));
    }

    // 가게 내 미션들 조회
    @GetMapping("/stores/{storeId}/missions")
    public ApiResponse<MissionResDTO.Pagination<MissionResDTO.GetMission>> getMissions(
            @PathVariable Long storeId,
            @RequestParam Integer pageSize,
            @RequestParam String cursor,
            @RequestParam String query
    ){
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.success(code, missionService.getMissions(storeId, pageSize, cursor, query));
    }


}

