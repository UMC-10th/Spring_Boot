package com.example.Spring_Boot.domain.review.converter;

import com.example.Spring_Boot.domain.member.entity.Member;
import com.example.Spring_Boot.domain.review.dto.ReviewReqDTO;
import com.example.Spring_Boot.domain.review.dto.ReviewResDTO;
import com.example.Spring_Boot.domain.review.entity.Review;
import com.example.Spring_Boot.domain.review.enums.ReviewSortType;
import com.example.Spring_Boot.domain.store.entity.Store;
import org.springframework.data.domain.Slice;

import java.util.List;

public class ReviewConverter {

    private ReviewConverter() {
    }

    public static Review toReview(
            ReviewReqDTO.CreateReviewRequest request,
            Member member,
            Store store
    ) {
        return Review.builder()
                .content(request.content())
                .rating(request.rating())
                .member(member)
                .store(store)
                .build();
    }

    public static ReviewResDTO.CreateReviewResponse toCreateReviewResponse(Review review) {
        return ReviewResDTO.CreateReviewResponse.builder()
                .reviewId(review.getReviewId())
                .storeId(review.getStore().getStoreId())
                .content(review.getContent())
                .imageUrls(List.of())
                .rating(review.getRating())
                .build();
    }

    public static ReviewResDTO.MyReviewListResponse toMyReviewListResponse(
            Slice<Review> reviewSlice,
            ReviewSortType sortType
    ) {
        return ReviewResDTO.MyReviewListResponse.builder()
                .reviewList(reviewSlice.getContent().stream()
                        .map(ReviewConverter::toMyReviewInfo)
                        .toList())
                .pageInfo(toCursorInfo(reviewSlice, sortType))
                .build();
    }

    private static ReviewResDTO.MyReviewInfo toMyReviewInfo(Review review) {
        return ReviewResDTO.MyReviewInfo.builder()
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .rating(review.getRating())
                .reply(review.getReply())
                .storeInfo(toStoreInfo(review.getStore()))
                .build();
    }

    private static ReviewResDTO.StoreInfo toStoreInfo(Store store) {
        return ReviewResDTO.StoreInfo.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .build();
    }

    private static ReviewResDTO.CursorInfo toCursorInfo(
            Slice<Review> reviewSlice,
            ReviewSortType sortType
    ) {
        String nextCursor = null;
        if (reviewSlice.hasNext() && !reviewSlice.getContent().isEmpty()) {
            Review lastReview = reviewSlice.getContent().get(reviewSlice.getNumberOfElements() - 1);
            nextCursor = switch (sortType) {
                case ID -> "ID:" + lastReview.getReviewId();
                case RATING -> "RATING:" + lastReview.getRating() + ":" + lastReview.getReviewId();
            };
        }

        return ReviewResDTO.CursorInfo.builder()
                .hasNext(reviewSlice.hasNext())
                .nextCursor(nextCursor)
                .size(reviewSlice.getNumberOfElements())
                .build();
    }
}
