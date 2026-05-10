package com.umc.umc10th.domain.notification.entity;

import com.umc.umc10th.domain.notification.enums.Category;
import com.umc.umc10th.domain.store.entity.Store;
import com.umc.umc10th.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Column(name = "content", nullable = false, length = 50)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}