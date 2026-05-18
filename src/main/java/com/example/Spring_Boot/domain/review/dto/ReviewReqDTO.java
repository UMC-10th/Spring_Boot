package com.example.Spring_Boot.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

public class ReviewReqDTO {

    @Builder
    public record CreateReviewRequest(
            @NotBlank(message = "리뷰 내용은 필수입니다.")
            @Size(max = 255, message = "리뷰 내용은 255자 이하로 입력해야 합니다.")
            String content,
            List<String> imageUrls,

            @NotNull(message = "평점은 필수입니다.")
            @Min(value = 1, message = "평점은 1점 이상이어야 합니다.")
            @Max(value = 5, message = "평점은 5점 이하이어야 합니다.")
            Integer rating
    ) {}

    @Builder
    public record MyReviewRequest(
            @NotNull(message = "유저 ID는 필수입니다.")
            @Positive(message = "유저 ID는 양수여야 합니다.")
            Long userId
    ) {}
}
