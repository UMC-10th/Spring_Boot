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
}