package com.example.umc10th.domain.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

    @Builder
    public record MyReviewDTO(
            Long reviewId,
            Long storeId,
            String storeName,
            String content,
            Integer rating,
            LocalDateTime createdAt
    ) {
    }

    @Builder
    public record CursorPagination<T>(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ) {
    }

    @Builder
    public record CreateReviewResultDTO(
            Long reviewId,
            String content,
            Integer rating,
            LocalDateTime createdAt
    ) {
    }
}