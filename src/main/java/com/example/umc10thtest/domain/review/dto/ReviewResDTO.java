package com.example.umc10thtest.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ReviewResDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class WriteReviewRes {
        private Long reviewId;
        private LocalDateTime createdAt;
    }
}
