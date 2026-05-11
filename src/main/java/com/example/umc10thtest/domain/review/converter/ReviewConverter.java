package com.example.umc10thtest.domain.review.converter;

import com.example.umc10thtest.domain.member.entity.Member;
import com.example.umc10thtest.domain.review.dto.ReviewReqDTO;
import com.example.umc10thtest.domain.review.dto.ReviewResDTO;
import com.example.umc10thtest.domain.review.entity.Review;
import com.example.umc10thtest.domain.store.entity.Store;

public class ReviewConverter {

    public static Review toReview(ReviewReqDTO.WriteReviewReq request, Member member, Store store) {
        return Review.builder()
                .score(request.getScore())
                .body(request.getBody())
                .member(member)
                .store(store)
                .build();
    }

    public static ReviewResDTO.WriteReviewRes toWriteReviewRes(Review review) {
        return ReviewResDTO.WriteReviewRes.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
