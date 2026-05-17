package umc.domain.review.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // ID 순 커서 페이징
    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.user.id = :userId AND r.id < :cursorId ORDER BY r.id DESC")
    Slice<Review> findMyReviewsByIdCursor(@Param("userId") Long userId, @Param("cursorId") Long cursorId, PageRequest pageRequest);

    // ID 순 처음 조회(커서 없는 경우)
    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.user.id = :userId ORDER BY r.id DESC")
    Slice<Review> findMyReviewsOrderByIdDesc(@Param("userId") Long userId, PageRequest pageRequest);

    // 별점 순 커서 페이징(별점 같을시 ID 최신순)
    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.user.id = :userId AND (r.star < :cursorStar OR (r.star = :cursorStar AND r.id < :cursorId)) ORDER BY r.star DESC, r.id DESC")
    Slice<Review> findMyReviewsByStarCursor(@Param("userId") Long userId, @Param("cursorStar") Float cursorStar, @Param("cursorId") Long cursorId, PageRequest pageRequest);

    // 별점 순 처음 조회(커서 없는 경우)
    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.user.id = :userId ORDER BY r.star DESC, r.id DESC")
    Slice<Review> findMyReviewsOrderByStarDesc(@Param("userId") Long userId, PageRequest pageRequest);
}