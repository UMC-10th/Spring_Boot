package com.umc.umc10th.domain.review.entity;


import com.umc.umc10th.domain.review.enums.Stars;
import com.umc.umc10th.domain.store.entity.Store;
import com.umc.umc10th.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(name = "stars", nullable = false)
    private Stars stars;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "picture", columnDefinition = "TEXT")
    private String picture;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public Review(User user, String content, Stars stars, Store store) {
        this.user = user;
        this.content = content;
        this.stars = stars;
        this.store = store;
    }
}