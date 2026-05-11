package umc.domain.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import umc.domain.mission.dto.response.MissionResponseDto;
import umc.domain.mission.service.MissionQueryService;
import umc.domain.user.entity.mapping.MissionStatus;
import umc.domain.user.entity.mapping.UserMission;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.status.GeneralSuccessCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController {
    private final MissionQueryService missionQueryService;

    @GetMapping("/my")
    public ApiResponse<MissionResponseDto.MissionListDto> getMyMissions(
            @RequestHeader(name = "userId") Long userId,
            @RequestParam(name = "status") MissionStatus status,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        Page<UserMission> userMissionPage = missionQueryService.getMyMissions(userId, status, page-1);

        List<String> missionNames = userMissionPage.stream()
                .map(um -> um.getMission().getMissionSpec())
                .collect(Collectors.toList());

        MissionResponseDto.MissionListDto dummyResponse = MissionResponseDto.MissionListDto.builder()
                .missionNames(List.of("Todo1", "Todo2"))
                .listSize(2)
                .totalPage(1)
                .totalElements(2L)
                .isFirst(true)
                .isLast(true)
                .build();
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, dummyResponse);
    }

    // 4. 미션 성공 API
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
