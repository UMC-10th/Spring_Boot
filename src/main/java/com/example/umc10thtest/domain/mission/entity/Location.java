package com.example.umc10thtest.domain.mission.entity;

import com.example.umc10thtest.domain.mission.enums.Address;
import com.example.umc10thtest.domain.store.entity.Store;
import com.example.umc10thtest.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "location")
public class Location extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Address address;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Store> storeList = new ArrayList<>();
}
