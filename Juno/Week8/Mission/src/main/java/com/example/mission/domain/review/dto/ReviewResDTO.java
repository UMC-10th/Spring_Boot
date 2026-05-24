package com.example.mission.domain.review.dto;

import lombok.Builder;
import java.util.List;

public class ReviewResDTO {

    @Builder
    public record GetReview(
            Long reviewId,
            String content,
            Float score
    ){}

    @Builder
    public record Pagination<T>(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){}
}
