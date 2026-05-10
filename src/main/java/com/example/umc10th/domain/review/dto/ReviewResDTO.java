package com.example.umc10th.domain.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

    // 리뷰 작성 응답
    @Builder
    public record CreateReview(
            Long reviewId,
            Long storeId,
            Float star,
            String content,
            List<String> photoUrls,
            LocalDateTime createdAt
    ) {}
}

