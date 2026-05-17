package com.example.umc10thtest.domain.mission.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

public class MissionReqDTO {

    @Getter
    public static class MissionListReq {
        private Long locationId;
        private Long memberId;
    }

    @Getter
    public static class CreateMissionReq {
        @NotNull(message = "마감일은 필수입니다.")
        private LocalDate deadline;

        @NotNull(message = "포인트는 필수입니다.")
        @Min(value = 0, message = "포인트는 0 이상이어야 합니다.")
        private Integer point;

        @NotBlank(message = "미션 조건은 필수입니다.")
        private String conditional;
    }

    @Getter
    public static class ProgressingMissionsReq {
        @NotNull(message = "사용자 ID는 필수입니다.")
        private Long memberId;

        @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
        private Integer page = 0;

        @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
        private Integer pageSize = 10;
    }
}
