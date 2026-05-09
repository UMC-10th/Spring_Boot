package com.example.umc10th.domain.store.entity;

import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    private String name;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "store")
    private List<Mission> missionList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Review> reviewList = new ArrayList<>();
}