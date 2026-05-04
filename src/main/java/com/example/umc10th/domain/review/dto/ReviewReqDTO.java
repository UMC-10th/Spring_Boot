package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
}