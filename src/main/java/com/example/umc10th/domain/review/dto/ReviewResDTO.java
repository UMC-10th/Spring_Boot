package com.example.umc10th.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ReviewResDTO {

    @Builder
    @Getter
    public static class CreateResult {
        private Long reviewId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    public static class MyReviewItem {
        private Long reviewId;
        private String restaurantName;
        private Integer rating;
        private String content;
        private LocalDateTime createdAt;
    }
}