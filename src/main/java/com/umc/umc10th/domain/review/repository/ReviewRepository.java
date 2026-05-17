package com.umc.umc10th.domain.review.repository;


import com.umc.umc10th.domain.review.entity.Review;
import com.umc.umc10th.domain.review.enums.Stars;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
            SELECT r FROM Review r
            JOIN FETCH r.user u
            WHERE r.user.id = :userId
            ORDER BY r.createdAt DESC
            """)
    Page<Review> findMyReviews(
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query("""
            SELECT COUNT(r) > 0 FROM Review r
            WHERE r.store.id = :storeId AND r.user.id = :userId
            """)
    boolean existsByStoreAndUser(
            @Param("storeId") Long storeId,
            @Param("userId") Long userId
    );

    Slice<Review> findReviewsByUser_IdOrderByIdDesc(
            Long userId,
            Pageable pageable
    );

    Slice<Review> findReviewsByUser_IdAndIdLessThanOrderByIdDesc(
            Long userId,
            Long idCursor,
            Pageable pageable
    );

    Slice<Review> findReviewsByUser_IdOrderByStarsDescIdDesc(
            Long userId,
            Pageable pageable
    );

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId " +
            "AND (r.stars < :starsCursor OR (r.stars = :starsCursor AND r.id < :idCursor)) " +
            "ORDER BY r.stars DESC, r.id DESC")
    Slice<Review> findReviewsByUser_IdOrderByStarsDesc(
            @Param("userId") Long userId,
            @Param("starsCursor") Stars starsCursor,
            @Param("idCursor") Long idCursor,
            Pageable pageable
    );
}