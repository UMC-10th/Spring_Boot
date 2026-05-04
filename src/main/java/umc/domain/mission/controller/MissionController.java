package umc.domain.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.domain.mission.dto.response.MissionResponseDto;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.status.GeneralSuccessCode;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController {

    @GetMapping("/my")
    public ApiResponse<MissionResponseDto.MissionListDto> getMyMissions(
            @RequestParam(name = "status") String status,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size
    ) {
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
