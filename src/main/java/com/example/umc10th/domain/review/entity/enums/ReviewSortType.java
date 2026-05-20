package com.example.umc10th.domain.review.entity.enums;

/**
 * 내 리뷰 조회 시 정렬 기준.
 * - ID: 작성 순서 (review_id DESC, 최신 작성 리뷰가 먼저)
 * - RATING: 별점 순 (rating DESC, 동점은 id DESC)
 */
public enum ReviewSortType {
    ID,
    RATING
}