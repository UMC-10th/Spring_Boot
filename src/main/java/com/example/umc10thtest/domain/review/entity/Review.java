package com.example.umc10thtest.domain.review.entity;

import com.example.umc10thtest.domain.member.entity.Member;
import com.example.umc10thtest.domain.store.entity.Store;
import com.example.umc10thtest.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float score;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewPhoto> reviewPhotoList = new ArrayList<>();

    @OneToOne(mappedBy = "review", cascade = CascadeType.ALL)
    private Reply reply;
}
