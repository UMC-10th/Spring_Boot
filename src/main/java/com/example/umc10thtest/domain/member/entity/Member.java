package com.example.umc10thtest.domain.member.entity;

import com.example.umc10thtest.domain.member.enums.Gender;
import com.example.umc10thtest.domain.member.enums.SocialType;
import com.example.umc10thtest.domain.mission.entity.mapping.MemberMission;
import com.example.umc10thtest.domain.review.entity.Review;
import com.example.umc10thtest.domain.member.entity.mapping.MemberFood;
import com.example.umc10thtest.domain.member.entity.mapping.MemberTerm;
import com.example.umc10thtest.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nickname;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private Integer point;
    private String profileUrl;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberFood> memberFoodList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberTerm> memberTermList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberMission> memberMissionList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();
}
