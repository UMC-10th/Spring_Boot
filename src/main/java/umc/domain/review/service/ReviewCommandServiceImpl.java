package umc.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.domain.mission.entity.Mission;
import umc.domain.mission.repository.MissionRepository;
import umc.domain.review.dto.request.ReviewRequestDto;
import umc.domain.review.entity.Review;
import umc.domain.review.repository.ReviewRepository;
import umc.domain.user.entity.User;
import umc.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;

    public Review createReview(Long userId, Long missionId, ReviewRequestDto.CreateReviewDto request) {
        // 유저 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

        //  미션 찾기
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("해당 미션을 찾을 수 없습니다."));

        Review review = Review.builder()
                .user(user)
                .store(mission.getStore())
                .star(request.star().floatValue())
                .content(request.content())
                .build();

        return reviewRepository.save(review);
    }
}