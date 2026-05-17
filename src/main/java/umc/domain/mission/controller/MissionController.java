package umc.domain.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import umc.domain.mission.dto.response.MissionResponseDto;

import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.status.GeneralSuccessCode;
import umc.global.common.PageResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController {

    private final MissionQueryService missionQueryService;

    @GetMapping("/my")
    public ApiResponse<PageResponseDto<MissionResponseDto.MyMissionPreviewDto>> getMyMissions(
            @RequestHeader(name = "userId") Long userId,
            @RequestParam(name = "status") MissionStatus status,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size
    ) {
        // JPA 페이지는 0부터 시작하므로 page - 1 처리
        Page<UserMission> userMissionPage = missionQueryService.getMyMissions(userId, status, page - 1);

        // Entity 리스트를 7주차 응답 규격인 DTO 리스트로 변환
        List<MissionResponseDto.MyMissionPreviewDto> data = userMissionPage.stream()
                .map(um -> MissionResponseDto.MyMissionPreviewDto.builder()
                        .missionId(um.getMission().getId())
                        .storeName(um.getMission().getStore().getName())
                        .reward(um.getMission().getReward())
                        .missionSpec(um.getMission().getMissionSpec())
                        .deadline(um.getMission().getDeadline())
                        .build())
                .collect(Collectors.toList());

        // 공통 페이징 응답 객체 포장
        PageResponseDto<MissionResponseDto.MyMissionPreviewDto> response = PageResponseDto.<MissionResponseDto.MyMissionPreviewDto>builder()
                .data(data)
                .listSize(userMissionPage.getSize())
                .totalPage(userMissionPage.getTotalPages())
                .totalElements(userMissionPage.getTotalElements())
                .isFirst(userMissionPage.isFirst())
                .isLast(userMissionPage.isLast())
                .build();

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, response);
    }

    @PatchMapping("/{missionId}/complete")
    public ApiResponse<MissionResponseDto.CompleteMissionResultDto> completeMission(
            @PathVariable(name = "missionId") Long missionId
    ) {
        MissionResponseDto.CompleteMissionResultDto dummyResponse = MissionResponseDto.CompleteMissionResultDto.builder()
                .missionId(missionId)
                .completedAt(LocalDateTime.now())
                .build();
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, dummyResponse);
    }
}