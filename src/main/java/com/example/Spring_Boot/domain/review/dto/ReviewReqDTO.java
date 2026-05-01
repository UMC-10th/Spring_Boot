package com.example.Spring_Boot.domain.review.dto;

import java.util.List;

public class ReviewReqDTO {

    public record CreateReviewRequest(
            String content,
            List<String> imageUrls,
            Integer rating
    ) {}
}
