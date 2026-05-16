package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private static final String INITIAL_CURSOR = "-1";

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public ReviewResDTO.CursorPagination<ReviewResDTO.MyReviewDTO> getMyReviews(
            Long memberId,
            Integer pageSize,
            String cursor,
            String sort
    ) {
        String normalizedSort = sort.trim().toLowerCase();
        if (!normalizedSort.equals("id") && !normalizedSort.equals("rating")) {
            throw new ReviewException(ReviewErrorCode.INVALID_SORT);
        }

        PageRequest pageRequest = PageRequest.of(0, pageSize);
        Slice<Review> slice;

        if (INITIAL_CURSOR.equals(cursor)) {
            slice = switch (normalizedSort) {
                case "id" -> reviewRepository.findByMember_IdOrderByIdDesc(memberId, pageRequest);
                case "rating" -> reviewRepository.findByMember_IdOrderByRatingDescIdDesc(
                        memberId,
                        pageRequest
                );
                default -> throw new ReviewException(ReviewErrorCode.INVALID_SORT);
            };
        } else {
            String[] parts = cursor.split(":");
            if (parts.length != 2) {
                throw new ReviewException(ReviewErrorCode.INVALID_CURSOR);
            }
            try {
                slice = switch (normalizedSort) {
                    case "id" -> {
                        long idBound = Long.parseLong(parts[1]);
                        yield reviewRepository.findByMember_IdAndIdLessThanOrderByIdDesc(
                                memberId,
                                idBound,
                                pageRequest
                        );
                    }
                    case "rating" -> {
                        int lastRating = Integer.parseInt(parts[0]);
                        long lastId = Long.parseLong(parts[1]);
                        yield reviewRepository.sliceByMember_IdAndRatingCursor(
                                memberId,
                                lastRating,
                                lastId,
                                pageRequest
                        );
                    }
                    default -> throw new ReviewException(ReviewErrorCode.INVALID_SORT);
                };
            } catch (NumberFormatException ex) {
                throw new ReviewException(ReviewErrorCode.INVALID_CURSOR);
            }
        }

        List<ReviewResDTO.MyReviewDTO> data =
                slice.getContent().stream().map(ReviewConverter::toMyReview).toList();

        String nextCursor = null;
        if (slice.hasNext() && !slice.getContent().isEmpty()) {
            Review last = slice.getContent().getLast();
            nextCursor = switch (normalizedSort) {
                case "id" -> last.getId() + ":" + last.getId();
                case "rating" -> last.getRating() + ":" + last.getId();
                default -> null;
            };
        }

        return ReviewConverter.toCursorPagination(data, slice.hasNext(), nextCursor, pageSize);
    }

    @Transactional
    public ReviewResDTO.CreateReviewResultDTO createReview(
            ReviewReqDTO.CreateReviewDTO request
    ) {

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow();

        Store store = storeRepository.findById(request.storeId())
                .orElseThrow();

        Review review = ReviewConverter.toReview(request, member, store);

        Review savedReview = reviewRepository.save(review);

        return ReviewConverter.toCreateReviewResultDTO(savedReview);
    }
}