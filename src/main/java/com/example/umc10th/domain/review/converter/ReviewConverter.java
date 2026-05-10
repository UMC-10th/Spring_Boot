package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;

import java.util.List;

public class ReviewConverter {

    // 리뷰 엔티티 변환
    public static Review toReview(ReviewReqDTO.CreateReview dto, Member member, Store store) {
        return Review.builder()
                .content(dto.content())
                .star(dto.star())
                .member(member)
                .store(store)
                .build();
    }

    // 리뷰 작성 응답
    public static ReviewResDTO.CreateReview toCreateReview(Review review) {
        return ReviewResDTO.CreateReview.builder()
                .reviewId(review.getId())
                .storeId(review.getStore().getId())
                .star(review.getStar())
                .content(review.getContent())
                .photoUrls(List.of())           // 사진 배제
                .createdAt(review.getCratedAt())
                .build();
    }
}
