package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.global.enums.Address;
import com.example.umc10th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "location")
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false)
    private Long id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 50)
    private Address name = Address.NONE;

}
