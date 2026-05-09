package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.store.entity.Store;

public class ReviewConverter {

    public static Review toReview(
            ReviewReqDTO.CreateReviewDTO request,
            Member member,
            Store store
    ) {

        return Review.builder()
                .member(member)
                .store(store)
                .content(request.content())
                .rating(request.rating())
                .build();
    }

    public static ReviewResDTO.CreateReviewResultDTO toCreateReviewResultDTO(
            Review review
    ) {

        return ReviewResDTO.CreateReviewResultDTO.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();
    }
}