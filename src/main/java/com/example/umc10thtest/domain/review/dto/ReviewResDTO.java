package com.example.umc10thtest.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class WriteReviewRes {
        private Long reviewId;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MyReviewRes {
        private Long reviewId;
        private Float score;
        private String body;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MyReviewListRes {
        private List<MyReviewRes> reviewList;
        private Integer listSize;
        private Boolean hasNext;
        private String nextCursor;
    }
}
