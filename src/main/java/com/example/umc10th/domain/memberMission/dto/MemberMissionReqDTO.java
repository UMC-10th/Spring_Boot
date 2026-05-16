package com.example.umc10th.domain.memberMission.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MemberMissionReqDTO {

    /** 진행 중인 미션 조회 (오프셋 페이지네이션) */
    public record OngoingMissionsQuery(
            @NotNull(message = "회원 ID는 필수입니다.")
            Long memberId,
            @NotNull(message = "페이지 번호는 필수입니다.")
            @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
            Integer page,
            @NotNull(message = "페이지 크기는 필수입니다.")
            @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
            @Max(value = 100, message = "페이지 크기는 100 이하여야 합니다.")
            Integer pageSize
    ) {
    }
}

