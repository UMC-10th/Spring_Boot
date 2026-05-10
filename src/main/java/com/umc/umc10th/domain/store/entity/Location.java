package com.umc.umc10th.domain.store.entity;

import com.umc.umc10th.domain.mission.entity.Mission;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @Column(name = "location_name", nullable = false, columnDefinition = "TEXT")
    private String locationName;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Store> stores = new ArrayList<>();

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Mission> missions = new ArrayList<>();
}