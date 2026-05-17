package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MissionController {

	private final MissionService missionService;

	// 내 미션 목록 조회
	@GetMapping("/member-missions")
	public ApiResponse<MissionResDTO.MissionList> getMissionList(
			@ModelAttribute MissionReqDTO.MissionListRequest request
	) {
		return ApiResponse.onSuccess(
				GeneralSuccessCode.OK,
				missionService.getMissionList(request)
		);
	}

	// 미션 완료
	@PatchMapping("/member-missions/{memberMissionId}/complete")
	public ApiResponse<MissionResDTO.CompleteMission> completeMission(
			@PathVariable Long memberMissionId,
			@RequestBody @Valid MissionReqDTO.CompleteMission request
	) {
		return ApiResponse.onSuccess(
				GeneralSuccessCode.OK,
				missionService.completeMission(memberMissionId, request)
		);
	}

	// 진행중인 미션 조회
	@PostMapping("/member-missions/in-progress")
	public ApiResponse<MissionResDTO.MissionList> getInProgressMissions(
			@RequestBody @Valid MissionReqDTO.InProgressMissionRequest request
	) {
		return ApiResponse.onSuccess(
				GeneralSuccessCode.OK,
				missionService.getInProgressMissions(request)
		);
	}

	// 가게 미션 생성
	@PostMapping("/stores/{storeId}/missions")
	public ApiResponse<Void> createStoreMission(
			@PathVariable Long storeId,
			@RequestBody @Valid MissionReqDTO.CreateMission request
	) {
		missionService.createStoreMission(storeId, request);
		return ApiResponse.onSuccess(MissionSuccessCode.MISSION_CREATE_SUCCESS, null);
	}

	// 가게 내 미션 목록 조회
	@GetMapping("/stores/{storeId}/missions")
	public ApiResponse<MissionResDTO.StoreMissionList> getStoreMissions(
			@PathVariable Long storeId,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size
	) {
		return ApiResponse.onSuccess(
				MissionSuccessCode.STORE_MISSION_LIST_SUCCESS,
				missionService.getStoreMissions(storeId, page, size)
		);
	}
}