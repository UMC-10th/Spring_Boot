package com.example.umc10th.domain.review.entity;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long id;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "star", nullable = false)
    private BigDecimal star;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id", nullable = true)
    private Reply reply;

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ReviewPhoto> reviewPhotos = new ArrayList<>();
}