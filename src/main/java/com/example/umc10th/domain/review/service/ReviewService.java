package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.StoreException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.ReviewPhoto;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.exception.code.StoreErrorCode;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

	private static final Long CURRENT_MEMBER_ID = 1L;

	private final ReviewRepository reviewRepository;
	private final StoreRepository storeRepository;
	private final MemberRepository memberRepository;
	private final MemberMissionRepository memberMissionRepository;

	public ReviewResDTO.StoreInfo getStoreInfo(Long storeId) {
		Store store = storeRepository.findById(storeId)
				.orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));

		return ReviewConverter.toStoreInfoResponse(store);
	}

	@Transactional
	public ReviewResDTO.CreateReview createReview(Long storeId, ReviewReqDTO.CreateReview request) {
		Store store = storeRepository.findById(storeId)
				.orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));

		Member member = memberRepository.findById(CURRENT_MEMBER_ID)
				.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

		if (request.memberMissionId() != null) {
			memberMissionRepository.findById(request.memberMissionId())
					.orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));
		}

		Review review = Review.builder()
				.store(store)
				.member(member)
				.star(BigDecimal.valueOf(request.starRate()))
				.content(request.content())
				.build();

		if (request.photoUrls() != null) {
			request.photoUrls().forEach(url ->
					review.getReviewPhotos().add(ReviewPhoto.builder()
							.review(review)
							.photoUrl(url)
							.build())
			);
		}

		Review savedReview = reviewRepository.save(review);

		List<String> savedUrls = request.photoUrls() != null ? request.photoUrls() : List.of();
		return ReviewConverter.toCreateReviewResponse(savedReview, savedUrls);
	}

	public ReviewResDTO.CursorReviewList getMyReviewsWithCursor(ReviewReqDTO.CursorReviewRequest request) {
		int size = request.size() == null ? 10 : request.size();
		boolean isStar = "STAR".equalsIgnoreCase(request.sortType());

		List<Review> reviews;

		if (isStar) {
			BigDecimal cursorStar = request.cursorStar() != null
					? BigDecimal.valueOf(request.cursorStar()) : null;
			reviews = reviewRepository.findByMemberIdCursorByStar(
					request.memberId(),
					cursorStar,
					request.cursorId(),
					PageRequest.of(0, size + 1)
			);
		} else {
			reviews = reviewRepository.findByMemberIdCursorById(
					request.memberId(),
					request.cursorId(),
					PageRequest.of(0, size + 1)
			);
		}

		return ReviewConverter.toCursorReviewList(reviews, size);
	}

	public ReviewResDTO.MyReviews getMyReviews(ReviewReqDTO.MyReviewRequest request) {
		int page = request.page() == null ? 0 : request.page();
		int size = request.size() == null ? 10 : request.size();

		Page<Review> reviewPage = reviewRepository.findByMemberId(
				CURRENT_MEMBER_ID,
				PageRequest.of(page, size)
		);

		return ReviewConverter.toMyReviewsResponse(reviewPage);
	}
}
