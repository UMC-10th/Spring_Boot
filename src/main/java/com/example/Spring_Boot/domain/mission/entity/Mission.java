package com.example.Spring_Boot.domain.mission.entity;

import com.example.Spring_Boot.domain.store.entity.Store;
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
@Table(name = "mission")
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reward", nullable = false)
    private Integer reward;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "conditional", nullable = false)
    private String conditional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<UserMission> userMissions = new ArrayList<>();
}