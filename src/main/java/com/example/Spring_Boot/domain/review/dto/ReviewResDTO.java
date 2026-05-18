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

    @Builder
    public record MyReviewListResponse(
            List<MyReviewInfo> reviewList,
            CursorInfo pageInfo
    ) {}

    @Builder
    public record MyReviewInfo(
            Long reviewId,
            String content,
            Integer rating,
            String reply,
            StoreInfo storeInfo
    ) {}

    @Builder
    public record StoreInfo(
            Long storeId,
            String name
    ) {}

    @Builder
    public record CursorInfo(
            Boolean hasNext,
            String nextCursor,
            Integer size
    ) {}
}
