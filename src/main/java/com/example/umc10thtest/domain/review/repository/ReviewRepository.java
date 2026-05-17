package com.example.umc10thtest.domain.review.repository;

import com.example.umc10thtest.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // ID 기준 정렬 — 커서 없음
    @Query("SELECT r FROM Review r WHERE r.member.id = :memberId ORDER BY r.id DESC")
    Slice<Review> findByMemberIdOrderByIdDesc(@Param("memberId") Long memberId, Pageable pageable);

    // ID 기준 정렬 — 커서 있음 (cursor: "ID:value")
    @Query("SELECT r FROM Review r WHERE r.member.id = :memberId AND r.id < :cursorId ORDER BY r.id DESC")
    Slice<Review> findByMemberIdAndIdBeforeCursor(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    // 별점 기준 정렬 — 커서 없음
    @Query("SELECT r FROM Review r WHERE r.member.id = :memberId ORDER BY r.score DESC, r.id DESC")
    Slice<Review> findByMemberIdOrderByScoreDesc(@Param("memberId") Long memberId, Pageable pageable);

    // 별점 기준 정렬 — 커서 있음 (compound cursor: "SCORE:score:id")
    @Query("SELECT r FROM Review r WHERE r.member.id = :memberId " +
           "AND (r.score < :cursorScore OR (r.score = :cursorScore AND r.id < :cursorId)) " +
           "ORDER BY r.score DESC, r.id DESC")
    Slice<Review> findByMemberIdWithScoreCursor(
            @Param("memberId") Long memberId,
            @Param("cursorScore") Float cursorScore,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
