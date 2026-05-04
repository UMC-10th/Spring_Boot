package umc.domain.mission.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class MissionResponseDto {

    @Builder
    public record MissionListDto(
            List<String> missionNames,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ) {}

    @Builder
    public record CompleteMissionResultDto(
            Long missionId,
            LocalDateTime completedAt
    ) {}
}
