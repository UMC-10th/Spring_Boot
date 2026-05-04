package com.example.Spring_Boot.domain.review.dto;

import lombok.Builder;

import java.util.List;

public class ReviewReqDTO {

    @Builder
    public record CreateReviewRequest(
            String content,
            List<String> imageUrls,
            Integer rating
    ) {}
}
