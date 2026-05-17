package com.umc.umc10th.domain.review.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponseDto {
    @Builder
    public record GetMyReviews(
            List<ReviewItem> reviews
    ) {
        @Builder
        public record ReviewItem (
            Long reviewId,
            String nickname,
            String stars,
            String content,
            LocalDateTime createdAt
        ){}
    }

    @Builder
    public record GetMyReviewsPaged(
            List<ReviewItem> reviews,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ) {
        @Builder
        public record ReviewItem(
                Long reviewId,
                String nickname,
                String stars,
                String content,
                LocalDateTime createdAt
        ) {}
    }
}
