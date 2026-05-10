package com.example.umc10th.domain.member.entity;

import com.example.umc10th.domain.member.entity.mapping.MemberFood;
import com.example.umc10th.domain.member.enums.FoodName;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id", nullable = false)
    private Long id;

    @Builder.Default
    @Column(name = "name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private FoodName name = FoodName.NONE;

    // 편의 생성자/팩토리 메서드 또는 기본값 처리
    public static Food of(FoodName name) {
        return Food.builder()
                .name(name == null ? FoodName.NONE : name)
                .build();
    }

    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberFood> memberFoods = new ArrayList<>();
}
