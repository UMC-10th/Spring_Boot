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
    /**
     * Review 엔티티 → MyReviewItem DTO 단일 매핑.
     * restaurant.restaurantName 접근 시 fetch join 덕분에 추가 쿼리 없음.
     */
    public static ReviewResDTO.MyReviewItem toMyReviewItem(Review review) {
        return ReviewResDTO.MyReviewItem.builder()
                .reviewId(review.getId())
                .restaurantName(review.getRestaurant().getRestaurantName())
                .rating(review.getRating())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }
}

