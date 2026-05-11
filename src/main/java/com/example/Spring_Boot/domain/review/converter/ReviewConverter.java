package com.example.Spring_Boot.domain.review.converter;

import com.example.Spring_Boot.domain.review.dto.ReviewReqDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewResDTO;
import com.example.Spring_Boot.domain.review.entity.Review;
import com.example.Spring_Boot.domain.store.entity.Store;
import com.example.Spring_Boot.domain.user.entity.User;

public class ReviewConverter {

    public static Review toReview(ReviewReqDTO.CreateReviewDTO request, User user, Store store) {
        return Review.builder()
                .rating(request.getRating())
                .content(request.getContent())
                .user(user)
                .store(store)
                .build();
    }

    public static ReviewResDTO.CreateReviewResultDTO toCreateReviewResultDTO(Review review) {
        return ReviewResDTO.CreateReviewResultDTO.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }
}