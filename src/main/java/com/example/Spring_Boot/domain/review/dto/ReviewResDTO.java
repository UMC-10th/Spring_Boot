package com.example.Spring_Boot.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewResDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReviewResultDTO {
        private Long reviewId;
        private String content;
        private Integer rating;
    }
}