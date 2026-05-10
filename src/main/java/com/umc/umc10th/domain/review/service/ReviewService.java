package com.umc.umc10th.domain.review.service;

import com.umc.umc10th.domain.review.entity.Review;
import com.umc.umc10th.domain.review.enums.Stars;
import com.umc.umc10th.domain.review.repository.ReviewRepository;
import com.umc.umc10th.domain.store.entity.Store;
import com.umc.umc10th.domain.store.repository.StoreRepository;
import com.umc.umc10th.domain.user.entity.User;
import com.umc.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    // 임시 인증 유저 ID - 실제로는 SecurityContextHolder 등에서 추출
    private static final Long TEMP_USER_ID = 1L;

    @Transactional
    public void createReview(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));

        User user = userRepository.findById(TEMP_USER_ID)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if (reviewRepository.existsByStoreAndUser(storeId, TEMP_USER_ID)) {
            throw new IllegalStateException("이미 리뷰를 작성하셨습니다.");
        }

        Review review = Review.builder()
                .user(user)
                .store(store)
                .stars(Stars.FIVE)  //mock
                .content("")        //mock
                .build();

        reviewRepository.save(review);
    }
}