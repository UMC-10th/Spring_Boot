package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"store"})
    Page<Review> findByMemberId(Long memberId, Pageable pageable);

    // 커서 기반 - ID순
    @Query("SELECT r FROM Review r " +
            "WHERE r.member.id = :memberId " +
            "AND (:cursorId IS NULL OR r.id < :cursorId) " +
            "ORDER BY r.id DESC")
    List<Review> findByMemberIdCursorById(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    // 커서 기반 - 별점순 (동점이면 id DESC)
    @Query("SELECT r FROM Review r " +
            "WHERE r.member.id = :memberId " +
            "AND (:cursorStar IS NULL OR r.star < :cursorStar " +
            "     OR (r.star = :cursorStar AND r.id < :cursorId)) " +
            "ORDER BY r.star DESC, r.id DESC")
    List<Review> findByMemberIdCursorByStar(
            @Param("memberId") Long memberId,
            @Param("cursorStar") BigDecimal cursorStar,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}