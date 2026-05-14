package com.example.umc10th.domain.member.entity;
import com.example.umc10th.domain.member.entity.enums.Gender;
import com.example.umc10th.domain.member.entity.enums.SocialType;
import com.example.umc10th.domain.member.entity.enums.Term;
import com.example.umc10th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "member_name", nullable = false, length = 30)
    private String name;

    @Column(name= "gender", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Builder.Default
    @Column(name = "point", nullable = false)
    private Integer point = 0;

    @Column(name = "login_provider", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "social_uid", nullable = false, length = 100)
    private String socialUid;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
