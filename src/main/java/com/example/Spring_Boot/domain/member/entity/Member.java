package com.example.Spring_Boot.domain.member.entity;

import com.example.Spring_Boot.domain.member.enums.Gender;
import com.example.Spring_Boot.domain.member.enums.SocialType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "user")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 5)
    private String name;

    @Column(nullable = false, length = 15)
    private String nickname;

    @Column(unique = true)
    private String email;

    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender = Gender.NONE;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Builder.Default
    @Column(nullable = false)
    private Integer point = 0;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "social_provider", nullable = false)
    private SocialType socialProvider = SocialType.LOCAL;

    @Column(name = "social_uid")
    private String socialUid;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
