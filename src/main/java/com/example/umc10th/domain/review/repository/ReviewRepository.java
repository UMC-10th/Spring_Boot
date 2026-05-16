package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = { "store" })
    Slice<Review> findByMember_IdOrderByIdDesc(Long memberId, Pageable pageable);

    @EntityGraph(attributePaths = { "store" })
    Slice<Review> findByMember_IdAndIdLessThanOrderByIdDesc(
            Long memberId,
            Long id,
            Pageable pageable
    );

    @EntityGraph(attributePaths = { "store" })
    Slice<Review> findByMember_IdOrderByRatingDescIdDesc(Long memberId, Pageable pageable);

    @EntityGraph(attributePaths = { "store" })
    @Query("""
            select r from Review r
            where r.member.id = :memberId
            and (r.rating < :lastRating or (r.rating = :lastRating and r.id < :lastId))
            order by r.rating desc, r.id desc
            """)
    Slice<Review> sliceByMember_IdAndRatingCursor(
            @Param("memberId") Long memberId,
            @Param("lastRating") Integer lastRating,
            @Param("lastId") Long lastId,
            Pageable pageable
    );
}
