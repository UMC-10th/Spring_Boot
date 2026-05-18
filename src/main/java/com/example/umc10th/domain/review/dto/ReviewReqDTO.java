package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

public class ReviewReqDTO {

    // 리뷰 작성
    @Builder
    public record CreateReview(
            @NotNull(message = "사용자 미션 ID는 필수입니다.")
            Long memberMissionId,

            @NotNull(message = "별점은 필수입니다.")
            @DecimalMin(value = "0.0", message = "별점은 0.0 이상이어야 합니다.")
            @DecimalMax(value = "5.0", message = "별점은 5.0 이하이어야 합니다.")
            BigDecimal starRate,

            @NotBlank(message = "리뷰 내용은 필수입니다.")
            @Size(min = 1, max = 1000, message = "리뷰 내용은 1자 이상 1000자 이하로 작성해야 합니다.")
            String content,

            @Size(max = 10, message = "사진은 최대 10장까지 업로드할 수 있습니다.")
            List<String> photoUrls
    ) {}
}