package umc.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.domain.mission.entity.Store;
import umc.domain.user.entity.User;
import umc.global.common.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 리뷰 내용
    private String content;

    // 별점
    private Float star;

    // 리뷰 작성 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 리뷰가 작성된 가게
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}