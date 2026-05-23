package umc.domain.mission.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.global.common.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String address;

    private Float score;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "region_id")
//     private Region region;
}