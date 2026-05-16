package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.global.dto.PageInfoDTO;
import com.example.umc10th.global.dto.SliceInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

public class ReviewConverter {

    // 요청 -> 엔티티
    public static Review toReview(ReviewReqDTO.CreateReview request, Member member, Store store) {
        return Review.builder()
                .content(request.content())
                .star(request.starRate())
                .member(member)
                .store(store)
                .build();
    }

    // 엔티티 -> 응답
    public static ReviewResDTO.CreateReview toCreateReview(Review review, List<String> photoUrls) {
        return ReviewResDTO.CreateReview.builder()
                .reviewId(review.getId())
                .storeId(review.getStore().getId())
                .memberId(review.getMember().getId())
                .starRate(review.getStar())
                .content(review.getContent())
                .photoUrls(photoUrls)
                .createdAt(review.getCreatedAt())
                .build();
    }

    // 내가 작성한 리뷰 단건 변환
    public static ReviewResDTO.MyReviewInfo toMyReviewInfo(Review review) {
        return ReviewResDTO.MyReviewInfo.builder()
                .reviewId(review.getId())
                .storeId(review.getStore().getId())
                .storeName(review.getStore().getName())
                .starRate(review.getStar())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }

    // 페이지 전체 변환
    public static ReviewResDTO.MyReviewList toMyReviewList(Page<Review> reviewPage) {
        List<ReviewResDTO.MyReviewInfo> reviews = reviewPage.getContent().stream()
                .map(ReviewConverter::toMyReviewInfo)
                .toList();

        return ReviewResDTO.MyReviewList.builder()
                .reviews(reviews)
                .pageInfo(PageInfoDTO.from(reviewPage))
                .build();
    }

    // Slice -> MyReviewSliceList 변환
    public static ReviewResDTO.MyReviewSliceList toMyReviewSliceList(
            Slice<Review> reviewSlice, String nextCursor
    ) {
        List<ReviewResDTO.MyReviewInfo> reviews = reviewSlice.getContent().stream()
                .map(ReviewConverter::toMyReviewInfo)
                .toList();

        SliceInfoDTO sliceInfo = SliceInfoDTO.builder()
                .nextCursor(nextCursor)
                .hasNext(reviewSlice.hasNext())
                .numberOfElements(reviewSlice.getNumberOfElements())
                .build();

        return ReviewResDTO.MyReviewSliceList.builder()
                .reviews(reviews)
                .sliceInfo(sliceInfo)
                .build();
    }
}
