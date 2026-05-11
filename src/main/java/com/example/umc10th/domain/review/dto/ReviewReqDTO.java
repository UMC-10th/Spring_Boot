package com.example.umc10th.domain.review.dto;

public class ReviewReqDTO {

    public record CreateReviewDTO(
            Long memberId,
            Long storeId,
            String content,
            Integer rating
    ) {
    }
}