package com.umc.umc10th.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "preferences")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preferences_id")
    private Long id;

    @Column(name = "category", nullable = false, length = 10)
    private String category;
}
