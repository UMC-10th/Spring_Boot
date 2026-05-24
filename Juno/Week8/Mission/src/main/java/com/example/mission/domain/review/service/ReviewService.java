package com.example.mission.domain.review.service;

import com.example.mission.domain.review.converter.ReviewConverter;
import com.example.mission.domain.review.dto.ReviewResDTO;
import com.example.mission.domain.review.entity.Review;
import com.example.mission.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResDTO.Pagination<ReviewResDTO.GetReview> getMyReviews(
            Long memberId,
            Integer pageSize,
            String cursor
    ) {
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        Slice<Review> reviewList;

        if (!cursor.equals("-1")) {
            Long idCursor = Long.parseLong(cursor.split(":")[0]);
            reviewList = reviewRepository.findReviewsByMember_IdAndIdLessThanOrderByIdDesc(memberId, idCursor, pageRequest);
        } else {
            reviewList = reviewRepository.findReviewsByMember_IdOrderByIdDesc(memberId, pageRequest);
        }

        String nextCursor = reviewList.getContent().isEmpty() ? null : 
            reviewList.getContent().getLast().getId() + ":" + reviewList.getContent().getLast().getId();

        return ReviewConverter.toPagination(
                reviewList.map(ReviewConverter::toGetReview).toList(),
                reviewList.hasNext(),
                nextCursor,
                reviewList.getSize()
        );
    }
}
