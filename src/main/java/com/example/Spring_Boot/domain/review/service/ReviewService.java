package com.example.Spring_Boot.domain.review.service;

import com.example.Spring_Boot.domain.member.entity.Member;
import com.example.Spring_Boot.domain.member.repository.MemberRepository;
import com.example.Spring_Boot.domain.review.converter.ReviewConverter;
import com.example.Spring_Boot.domain.review.dto.ReviewReqDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewResDTO;
import com.example.Spring_Boot.domain.review.entity.Review;
import com.example.Spring_Boot.domain.review.enums.ReviewSortType;
import com.example.Spring_Boot.domain.review.exception.ReviewException;
import com.example.Spring_Boot.domain.review.exception.code.ReviewErrorCode;
import com.example.Spring_Boot.domain.review.repository.ReviewRepository;
import com.example.Spring_Boot.domain.store.entity.Store;
import com.example.Spring_Boot.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private static final int MAX_CURSOR_SIZE = 50;

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ReviewResDTO.CreateReviewResponse createReview(Integer storeId, String authorization , ReviewReqDTO.CreateReviewRequest dto) {
        validateRating(dto.rating());

        Store store = storeRepository.findById(storeId.longValue())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.STORE_NOT_FOUND));
        Member member = memberRepository.findById(extractMemberId(authorization))
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.MEMBER_NOT_FOUND));

        Review review = ReviewConverter.toReview(dto, member, store);
        Review savedReview = reviewRepository.save(review);

        return ReviewConverter.toCreateReviewResponse(savedReview);
    }

    @Transactional(readOnly = true)
    public ReviewResDTO.MyReviewListResponse getMyReviews(
            ReviewReqDTO.MyReviewRequest request,
            ReviewSortType sortType,
            String cursor,
            int size
    ) {
        validateCursorSize(size);

        Pageable pageable = PageRequest.of(0, size);
        Slice<Review> reviewSlice = switch (sortType) {
            case ID -> {
                Long cursorId = parseIdCursor(cursor);
                yield reviewRepository.findMyReviewsOrderByIdDesc(request.userId(), cursorId, pageable);
            }
            case RATING -> {
                RatingCursor ratingCursor = parseRatingCursor(cursor);
                yield reviewRepository.findMyReviewsOrderByRatingDesc(
                        request.userId(),
                        ratingCursor.rating(),
                        ratingCursor.reviewId(),
                        pageable
                );
            }
        };

        return ReviewConverter.toMyReviewListResponse(reviewSlice, sortType);
    }

    private void validateRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new ReviewException(ReviewErrorCode.INVALID_RATING);
        }
    }

    private void validateCursorSize(int size) {
        if (size <= 0 || size > MAX_CURSOR_SIZE) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }
    }

    private Long parseIdCursor(String cursor) {
        if (isFirstCursor(cursor)) {
            return null;
        }

        String[] parts = cursor.split(":");
        if (parts.length != 2 || !ReviewSortType.ID.name().equals(parts[0])) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }

        return parseLong(parts[1]);
    }

    private RatingCursor parseRatingCursor(String cursor) {
        if (isFirstCursor(cursor)) {
            return new RatingCursor(null, null);
        }

        String[] parts = cursor.split(":");
        if (parts.length != 3 || !ReviewSortType.RATING.name().equals(parts[0])) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }

        Integer rating = parseInteger(parts[1]);
        if (rating < 1 || rating > 5) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }

        return new RatingCursor(rating, parseLong(parts[2]));
    }

    private boolean isFirstCursor(String cursor) {
        return cursor == null || cursor.isBlank() || "-1".equals(cursor);
    }

    private Long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR_REQUEST);
        }
    }

    private Long extractMemberId(String authorization) {
        if (authorization == null || authorization.isBlank()) {
            throw new ReviewException(ReviewErrorCode.INVALID_AUTHORIZATION);
        }

        try {
            return Long.parseLong(authorization.replace("Bearer", "").trim());
        } catch (NumberFormatException e) {
            throw new ReviewException(ReviewErrorCode.INVALID_AUTHORIZATION);
        }
    }

    private record RatingCursor(Integer rating, Long reviewId) {
    }
}
