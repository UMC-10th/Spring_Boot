package com.example.Spring_Boot.domain.review.converter;

import com.example.Spring_Boot.domain.member.entity.Member;
import com.example.Spring_Boot.domain.review.dto.ReviewReqDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewResDTO;
import com.example.Spring_Boot.domain.review.entity.Review;
import com.example.Spring_Boot.domain.store.entity.Store;

import java.util.List;

public class ReviewConverter {

    private ReviewConverter() {
    }

    public static Review toReview(
            ReviewReqDTO.CreateReviewRequest request,
            Member member,
            Store store
    ) {
        return Review.builder()
                .content(request.content())
                .rating(request.rating())
                .member(member)
                .store(store)
                .build();
    }

    public static ReviewResDTO.CreateReviewResponse toCreateReviewResponse(Review review) {
        return ReviewResDTO.CreateReviewResponse.builder()
                .reviewId(review.getReviewId())
                .storeId(review.getStore().getStoreId())
                .content(review.getContent())
                .imageUrls(List.of())
                .rating(review.getRating())
                .build();
    }
}
