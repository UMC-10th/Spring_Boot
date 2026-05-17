package com.example.Spring_Boot.domain.mission.entity;

import com.example.Spring_Boot.domain.mission.enums.MissionStatus;
import com.example.Spring_Boot.domain.user.entity.User;
import com.example.Spring_Boot.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_mission")
public class UserMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MissionStatus status = MissionStatus.IN_PROGRESS;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    public void complete() {
        this.status = MissionStatus.COMPLETE;
    }
}