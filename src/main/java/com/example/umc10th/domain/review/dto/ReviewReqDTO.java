package com.example.umc10th.domain.review.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

public class ReviewReqDTO {

    // 리뷰 작성
    @Builder
    public record CreateReview(
            Long memberMissionId,
            BigDecimal starRate,
            String content,
            List<String> photoUrls
    ) {}

}
