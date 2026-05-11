package com.example.Spring_Boot.domain.store.entity;

import com.example.Spring_Boot.domain.mission.entity.Mission;
import com.example.Spring_Boot.domain.mission.enums.Address;
import com.example.Spring_Boot.domain.review.entity.Review;
import com.example.Spring_Boot.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "store")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    @Enumerated(EnumType.STRING)
    private Address address;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Builder.Default
    private Float rating = 0.0f;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Mission> missions = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
}