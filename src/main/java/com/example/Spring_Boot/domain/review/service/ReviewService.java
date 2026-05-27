package com.example.Spring_Boot.domain.review.service;

import com.example.Spring_Boot.domain.member.entity.Member;
import com.example.Spring_Boot.domain.member.repository.MemberRepository;
import com.example.Spring_Boot.domain.review.converter.ReviewConverter;
import com.example.Spring_Boot.domain.review.dto.ReviewCursor;
import com.example.Spring_Boot.domain.review.dto.ReviewReqDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewResDTO;
import com.example.Spring_Boot.domain.review.entity.Review;
import com.example.Spring_Boot.domain.review.exception.ReviewException;
import com.example.Spring_Boot.domain.review.exception.code.ReviewErrorCode;
import com.example.Spring_Boot.domain.review.repository.ReviewRepository;
import com.example.Spring_Boot.domain.store.entity.Store;
import com.example.Spring_Boot.domain.store.repository.StoreRepository;
import com.example.Spring_Boot.global.security.auth.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ReviewResDTO.CreateReviewResponse createReview(Integer storeId, String authorization, ReviewReqDTO.CreateReviewRequest dto) {
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
            String authorization,
            ReviewCursor cursor
    ) {
        Long memberId = extractMemberId(authorization);
        Pageable pageable = PageRequest.of(0, cursor.size());

        Slice<Review> reviewSlice = switch (cursor.sortType()) {
            case ID -> reviewRepository.findMyReviewsOrderByIdDesc(memberId, cursor.reviewId(), pageable);
            case RATING -> {
                yield reviewRepository.findMyReviewsOrderByRatingDesc(
                        memberId,
                        cursor.rating(),
                        cursor.reviewId(),
                        pageable
                );
            }
        };

        return ReviewConverter.toMyReviewListResponse(reviewSlice, cursor.sortType());
    }

    private void validateRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new ReviewException(ReviewErrorCode.INVALID_RATING);
        }
    }

    private Long extractMemberId(String authorization) {
        return SecurityUtil.getCurrentMemberId()
                .orElseGet(() -> extractMemberIdFromAuthorization(authorization));
    }

    private Long extractMemberIdFromAuthorization(String authorization) {
        if (authorization == null || authorization.isBlank()) {
            throw new ReviewException(ReviewErrorCode.INVALID_AUTHORIZATION);
        }

        try {
            return Long.parseLong(authorization.replace("Bearer", "").trim());
        } catch (NumberFormatException e) {
            throw new ReviewException(ReviewErrorCode.INVALID_AUTHORIZATION);
        }
    }
}
