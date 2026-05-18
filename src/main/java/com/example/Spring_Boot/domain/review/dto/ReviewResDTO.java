package com.example.Spring_Boot.domain.review.dto;

import lombok.Builder;
import java.util.List;

public class ReviewResDTO {

    @Builder
    public record CreateReviewResultDTO(
            Long reviewId,
            String content,
            Integer rating
    ) {}

    // 내가 생성한 리뷰 조회 아이템
    @Builder
    public record GetReview(
            Long reviewId,
            String content,
            Integer rating,
            String createdAt
    ) {}
}