package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.restaurant.entity.Restaurant;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;

public class ReviewConverter {

    private ReviewConverter() {}

    public static Review toEntity(ReviewReqDTO.Create req,
                                  Member member,
                                  Restaurant restaurant) {
        return Review.builder()
                .content(req.content())
                .rating(req.rating())
                .member(member)
                .restaurant(restaurant)
                .build();
    }
    public static ReviewResDTO.CreateResult toCreateResult (Review review) {
        return ReviewResDTO.CreateResult.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }
}

