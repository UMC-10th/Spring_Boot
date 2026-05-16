package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.store.entity.Store;

import java.util.List;

public class ReviewConverter {

    public static ReviewResDTO.MyReviewDTO toMyReview(Review review) {
        return ReviewResDTO.MyReviewDTO.builder()
                .reviewId(review.getId())
                .storeId(review.getStore().getId())
                .storeName(review.getStore().getName())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static <T> ReviewResDTO.CursorPagination<T> toCursorPagination(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ) {
        return ReviewResDTO.CursorPagination.<T>builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .pageSize(pageSize)
                .build();
    }

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