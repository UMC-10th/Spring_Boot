package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReviewReqDTO {

    public record Create(
            @NotNull(message = "가게 ID는 필수입니다.")
            Long restaurantId,

            @NotNull
            @Min(value = 1, message = "평점은 최소 1점입니다.")
            @Max(value = 5, message = "평점은 최대 5점입니다.")
            Integer rating,

            @NotBlank(message = "리뷰 내용은 필수입니다.")
            String content
    ) {}

    /**
     * 내가 작성한 리뷰 목록 조회 요청 Body.
     * JWT 도입 전 임시로 사용자 ID를 Body로 받는다.
     */
    public record MyReviews(
            @NotNull(message = "사용자 ID는 필수입니다.")
            @Positive(message = "사용자 ID는 양수여야 합니다.")
            Long memberId
    ) {}
}