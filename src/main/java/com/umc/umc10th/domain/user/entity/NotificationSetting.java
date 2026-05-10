package com.umc.umc10th.domain.user.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_notification_settings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationSetting {

    @Id
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "mission_notifications_allowed", nullable = false)
    private Boolean missionNotificationsAllowed = false;

    @Column(name = "new_event_notifications_allowed", nullable = false)
    private Boolean newEventNotificationsAllowed = false;

    @Column(name = "question_answers_notification_allowed", nullable = false)
    private Boolean questionAnswersNotificationAllowed = false;

    @Column(name = "review_request_allowed", nullable = false)
    private Boolean reviewRequestAllowed = false;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
