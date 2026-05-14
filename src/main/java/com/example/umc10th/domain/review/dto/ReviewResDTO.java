package com.example.umc10th.domain.review.dto;

import com.example.umc10th.global.dto.PageInfoDTO;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

    // 가게 정보 조회 응답
    @Builder
    public record StoreInfo(
            Long storeId,
            String name,
            String foodType,
            String address
    ) {}

    // 리뷰 작성 응답
    @Builder
    public record CreateReview(
            Long reviewId,
            Long storeId,
            Long memberId,
            BigDecimal starRate,
            String content,
            List<String> photoUrls,
            LocalDateTime createdAt
    ) {}

    // 내가 작성한 리뷰 단건
    @Builder
    public record MyReviewInfo(
            Long reviewId,
            Long storeId,
            String storeName,
            BigDecimal starRate,
            String content,
            LocalDateTime createdAt
    ) {}

    // 내가 작성한 리뷰 목록
    @Builder
    public record MyReviewList(
            List<MyReviewInfo> reviews,
            PageInfoDTO pageInfo
    ) {}
}
