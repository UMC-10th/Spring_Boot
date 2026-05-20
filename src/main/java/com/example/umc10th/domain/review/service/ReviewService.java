package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.restaurant.entity.Restaurant;
import com.example.umc10th.domain.restaurant.repository.RestaurantRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.enums.ReviewSortType;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.global.apiPayload.dto.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public ReviewResDTO.CreateResult create(Long memberId, ReviewReqDTO.Create req) {
        Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Restaurant restaurant = restaurantRepository.findById(req.restaurantId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.RESTAURANT_NOT_FOUND));

        Review review = ReviewConverter.toEntity(req, member, restaurant);
        Review saved = reviewRepository.save(review);

        return ReviewConverter.toCreateResult(saved);
    }
    /**
     * 내가 작성한 리뷰 목록 — 커서 기반 페이지네이션.
     * sortBy에 따라 ID 순 또는 별점 순으로 정렬한다.
     */
    public CursorPage<ReviewResDTO.MyReviewItem> getMyReviews(
            Long memberId,
            ReviewSortType sortBy,
            Long cursorId,
            Integer cursorRating,
            Integer size
    ) {
        // 1. 회원 존재 확인
        if (memberRepository.findByIdAndDeletedAtIsNull(memberId).isEmpty()) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        // 2. size+1로 요청 (hasNext 판단용)
        //    LIMIT만 필요하므로 PageRequest의 정렬은 쓰지 않는다.
        //    실제 정렬은 JPQL의 ORDER BY가 책임진다.
        Pageable limit = PageRequest.of(0, size + 1);

        // 3. 정렬 기준에 따라 쿼리 분기
        List<Review> fetched = switch (sortBy) {
            case ID -> reviewRepository.findByMemberIdOrderByIdDesc(
                    memberId, cursorId, limit);
            case RATING -> reviewRepository.findByMemberIdOrderByRatingDesc(
                    memberId, cursorRating, cursorId, limit);
        };

        // 4. hasNext 판단 & 실제 반환 리스트 자르기
        boolean hasNext = fetched.size() > size;
        List<Review> page = hasNext ? fetched.subList(0, size) : fetched;

        // 5. 다음 커서 계산 — 마지막 요소 기준
        Long nextCursorId = null;
        Integer nextCursorRating = null;
        if (hasNext && !page.isEmpty()) {
            Review last = page.get(page.size() - 1);
            nextCursorId = last.getId();
            if (sortBy == ReviewSortType.RATING) {
                nextCursorRating = last.getRating();
            }
        }

        // 6. 엔티티 → DTO 매핑 후 CursorPage로 포장
        List<ReviewResDTO.MyReviewItem> items = page.stream()
                .map(ReviewConverter::toMyReviewItem)
                .toList();

        return CursorPage.<ReviewResDTO.MyReviewItem>builder()
                .items(items)
                .listSize(items.size())
                .hasNext(hasNext)
                .nextCursorId(nextCursorId)
                .nextCursorRating(nextCursorRating)
                .build();
    }
}
