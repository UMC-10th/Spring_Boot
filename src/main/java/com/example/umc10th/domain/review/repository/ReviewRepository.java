package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    long countByMemberId(Long memberId);

    /**
     * ID 순(review_id DESC) 커서 기반 조회.
     * cursorId가 null이면 첫 페이지로 간주해 조건을 무시한다.
     * fetch join으로 restaurant까지 한 번에 가져와 N+1을 방지한다.
     *
     * Pageable에는 size+1을 담아 hasNext 판단에 활용.
     */
    @Query("""
            SELECT r
            FROM Review r
            JOIN FETCH r.restaurant
            WHERE r.member.id = :memberId
              AND (:cursorId IS NULL OR r.id < :cursorId)
            ORDER BY r.id DESC
            """)
    List<Review> findByMemberIdOrderByIdDesc(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
    /**
     * 별점 순(rating DESC, 동점은 id DESC) 복합 키 커서 기반 조회.
     * 첫 요청이면 cursorRating, cursorId 모두 null.
     *
     * WHERE 조건 핵심:
     *   "내 별점보다 낮은 것" OR
     *   "별점은 같지만 ID가 작은 것"
     * 이게 복합 키 커서 페이징의 정석 패턴.
     */
    @Query("""
            SELECT r
            FROM Review r
            JOIN FETCH r.restaurant
            WHERE r.member.id = :memberId
              AND (
                    :cursorRating IS NULL
                    OR r.rating < :cursorRating
                    OR (r.rating = :cursorRating AND r.id < :cursorId)
                  )
            ORDER BY r.rating DESC, r.id DESC
            """)
    List<Review> findByMemberIdOrderByRatingDesc(
            @Param("memberId") Long memberId,
            @Param("cursorRating") Integer cursorRating,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
