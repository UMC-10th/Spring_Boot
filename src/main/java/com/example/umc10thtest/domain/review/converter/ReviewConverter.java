package com.example.umc10thtest.domain.review.converter;

import com.example.umc10thtest.domain.member.entity.Member;
import com.example.umc10thtest.domain.review.dto.ReviewReqDTO;
import com.example.umc10thtest.domain.review.dto.ReviewResDTO;
import com.example.umc10thtest.domain.review.entity.Review;
import com.example.umc10thtest.domain.store.entity.Store;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

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

    public static ReviewResDTO.MyReviewRes toMyReviewRes(Review review) {
        return ReviewResDTO.MyReviewRes.builder()
                .reviewId(review.getId())
                .score(review.getScore())
                .body(review.getBody())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewResDTO.MyReviewListRes toMyReviewListRes(Slice<Review> slice, String nextCursor) {
        List<ReviewResDTO.MyReviewRes> list = slice.getContent().stream()
                .map(ReviewConverter::toMyReviewRes)
                .collect(Collectors.toList());

        return ReviewResDTO.MyReviewListRes.builder()
                .reviewList(list)
                .listSize(list.size())
                .hasNext(slice.hasNext())
                .nextCursor(nextCursor)
                .build();
    }
}
