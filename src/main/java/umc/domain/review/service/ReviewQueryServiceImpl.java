package umc.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.review.entity.Review;
import umc.domain.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl {

    private final ReviewRepository reviewRepository;

    public Slice<Review> getMyReviews(Long userId, String cursor, String query, Integer size) {
        PageRequest pageRequest = PageRequest.of(0, size);

        if (query.equalsIgnoreCase("star")) {
            if (cursor.equals("-1")) {
                return reviewRepository.findMyReviewsOrderByStarDesc(userId, pageRequest);
            } else {
                String[] cursorSplit = cursor.split(":");
                Float cursorStar = Float.parseFloat(cursorSplit[0]);
                Long cursorId = Long.parseLong(cursorSplit[1]);
                return reviewRepository.findMyReviewsByStarCursor(userId, cursorStar, cursorId, pageRequest);
            }
        } else {
            if (cursor.equals("-1")) {
                return reviewRepository.findMyReviewsOrderByIdDesc(userId, pageRequest);
            } else {
                Long cursorId = Long.parseLong(cursor);
                return reviewRepository.findMyReviewsByIdCursor(userId, cursorId, pageRequest);
            }
        }
    }
}