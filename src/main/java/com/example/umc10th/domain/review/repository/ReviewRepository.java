package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ReviewRepository extends JpaRepository <Review, Long> {

    // 내가 작성한 리뷰 목록 (페이징 + Fetch Join)
    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.store " +
            "WHERE r.member.id = :memberId")
    Page<Review> findByMemberId(
            @Param("memberId") Long memberId,
            Pageable pageable
    );

    // 커서 기반 - ID 순 (첫 페이지: 커서 없음)
    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.store " +
            "WHERE r.member.id = :memberId " +
            "ORDER BY r.id DESC")
    Slice<Review> findFirstSliceByMemberId(
            @Param("memberId") Long memberId,
            Pageable pageable
    );

    // 커서 기반 - ID 순 (커서 있음)
    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.store " +
            "WHERE r.member.id = :memberId AND r.id < :cursorId " +
            "ORDER BY r.id DESC")
    Slice<Review> findNextSliceByMemberId(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    // 커서 기반 - 별점 순 (첫 페이지 : 커서 없음)
    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.store " +
            "WHERE r.member.id = :memberId " +
            "ORDER BY r.star DESC, r.id DESC")
    Slice<Review> findFirstSliceByStarDesc(
            @Param("memberId") Long memberId,
            Pageable pageable
    );

    // 커서 기반 - 별점 순 (커서 있음)
    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.store " +
            "WHERE r.member.id = :memberId " +
            "AND (r.star < :cursorStar " +
            "OR (r.star = :cursorStar AND r.id < :cursorId)) " +
            "ORDER BY r.star DESC, r.id DESC")
    Slice<Review> findNextSliceByStarDesc(
            @Param("memberId") Long memberId,
            @Param("cursorStar") BigDecimal cursorStar,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
