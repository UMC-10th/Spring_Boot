package com.example.Spring_Boot.domain.review.dto;

import lombok.Builder;

import java.util.List;

public class ReviewResDTO {

    @Builder
    public record CreateReviewResponse(
            Long reviewId,
            Long storeId,
            String content,
            List<String> imageUrls,
            Integer rating
    ) {}
}
