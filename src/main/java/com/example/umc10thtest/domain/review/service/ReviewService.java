package com.example.umc10thtest.domain.review.service;

import com.example.umc10thtest.domain.member.entity.Member;
import com.example.umc10thtest.domain.member.exception.MemberException;
import com.example.umc10thtest.domain.member.exception.code.MemberErrorCode;
import com.example.umc10thtest.domain.member.repository.MemberRepository;
import com.example.umc10thtest.domain.review.converter.ReviewConverter;
import com.example.umc10thtest.domain.review.dto.ReviewReqDTO;
import com.example.umc10thtest.domain.review.dto.ReviewResDTO;
import com.example.umc10thtest.domain.review.entity.Review;
import com.example.umc10thtest.domain.review.repository.ReviewRepository;
import com.example.umc10thtest.domain.store.entity.Store;
import com.example.umc10thtest.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public Review writeReview(Long storeId, Long memberId, ReviewReqDTO.WriteReviewReq request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Review review = ReviewConverter.toReview(request, member, store);
        return reviewRepository.save(review);
    }

    /**
     * 내가 작성한 리뷰 목록 조회 — 커서 기반 페이지네이션
     *
     * sortType: "ID"    → cursor 형식: "ID:reviewId"
     * sortType: "SCORE" → cursor 형식: "SCORE:score:reviewId" (복합 커서, 비유니크)
     * cursor가 null 이거나 "-1"이면 첫 페이지 조회
     */
    @Transactional(readOnly = true)
    public ReviewResDTO.MyReviewListRes getMyReviews(Long memberId, String cursor, String sortType, Integer pageSize) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(0, pageSize);
        Slice<Review> slice;

        boolean hasCursor = cursor != null && !cursor.equals("-1");

        if ("SCORE".equalsIgnoreCase(sortType)) {
            if (hasCursor) {
                // cursor: "SCORE:4.5:383"
                String[] parts = cursor.split(":");
                Float cursorScore = Float.parseFloat(parts[1]);
                Long cursorId = Long.parseLong(parts[2]);
                slice = reviewRepository.findByMemberIdWithScoreCursor(memberId, cursorScore, cursorId, pageRequest);
            } else {
                slice = reviewRepository.findByMemberIdOrderByScoreDesc(memberId, pageRequest);
            }
        } else {
            // 기본: ID 기준 정렬
            if (hasCursor) {
                // cursor: "ID:383"
                String[] parts = cursor.split(":");
                Long cursorId = Long.parseLong(parts[1]);
                slice = reviewRepository.findByMemberIdAndIdBeforeCursor(memberId, cursorId, pageRequest);
            } else {
                slice = reviewRepository.findByMemberIdOrderByIdDesc(memberId, pageRequest);
            }
        }

        // 다음 커서 계산
        String nextCursor = null;
        if (slice.hasNext()) {
            List<Review> content = slice.getContent();
            if (!content.isEmpty()) {
                Review last = content.get(content.size() - 1);
                if ("SCORE".equalsIgnoreCase(sortType)) {
                    nextCursor = "SCORE:" + last.getScore() + ":" + last.getId();
                } else {
                    nextCursor = "ID:" + last.getId();
                }
            }
        }

        return ReviewConverter.toMyReviewListRes(slice, nextCursor);
    }
}
