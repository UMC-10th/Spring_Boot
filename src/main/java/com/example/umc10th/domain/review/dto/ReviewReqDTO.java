package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewReqDTO {

    public record CreateReviewDTO(
            @NotNull(message = "회원 ID는 필수입니다.")
            Long memberId,
            @NotNull(message = "가게 ID는 필수입니다.")
            Long storeId,
            @NotBlank(message = "리뷰 내용은 필수입니다.")
            String content,
            @NotNull(message = "별점은 필수입니다.")
            @Min(value = 1, message = "별점은 1 이상이어야 합니다.")
            @Max(value = 5, message = "별점은 5 이하여야 합니다.")
            Integer rating
    ) {
    }
}
