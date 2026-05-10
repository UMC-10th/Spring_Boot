package com.example.umc10th.domain.review.dto;

import java.util.List;

public class ReviewReqDTO {

    // 리뷰 작성 (Security 미적용)

    public record CreateReview(
            Long memberId,
            Float star,
            String content,
            List<String> photoUrls
    ) {}
}

