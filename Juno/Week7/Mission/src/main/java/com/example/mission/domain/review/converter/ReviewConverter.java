package com.example.mission.domain.review.converter;

import com.example.mission.domain.review.dto.ReviewResDTO;
import com.example.mission.domain.review.entity.Review;

import java.util.List;

public class ReviewConverter {

    public static ReviewResDTO.GetReview toGetReview(Review review) {
        return ReviewResDTO.GetReview.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .score(review.getScore())
                .build();
    }

    public static <T> ReviewResDTO.Pagination<T> toPagination(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){
        return ReviewResDTO.Pagination.<T>builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .pageSize(pageSize)
                .build();
    }
}
