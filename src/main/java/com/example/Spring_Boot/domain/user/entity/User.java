package com.example.Spring_Boot.domain.user.entity;

import com.example.Spring_Boot.domain.mission.entity.UserMission;
import com.example.Spring_Boot.domain.mission.enums.Address;
import com.example.Spring_Boot.domain.review.entity.Review;
import com.example.Spring_Boot.domain.user.entity.mapping.UserFood;
import com.example.Spring_Boot.domain.user.entity.mapping.UserTerm;
import com.example.Spring_Boot.domain.user.enums.Gender;
import com.example.Spring_Boot.domain.user.enums.SocialType;
import com.example.Spring_Boot.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "address", nullable = false)
    @Enumerated(EnumType.STRING)
    private Address address;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Column(name = "social_uid", nullable = false)
    private String socialUid;

    @Column(name = "social_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Builder.Default
    private Integer point = 0;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<UserMission> userMissions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<UserFood> userFoods = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<UserTerm> userTerms = new ArrayList<>();
}