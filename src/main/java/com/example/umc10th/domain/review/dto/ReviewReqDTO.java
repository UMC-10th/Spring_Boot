package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ReviewReqDTO {

    // 리뷰 작성 (Security 미적용)

    public record CreateReview(
            @NotNull(message = "회원 아이디를 입력해주세요.")
            Long memberId,
            @NotNull(message = "별점을 입력해주세요.")
            Float star,
            String content,
            List<String> photoUrls
    ) {}

    public record GetMyReviews(
            @NotNull
            Long memberId
    ){}
}

