package com.example.umc10thtest.domain.member.entity;

import com.example.umc10thtest.domain.member.entity.mapping.MemberTerm;
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
@Table(name = "term")
public class Term extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;
    private Boolean optional;

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
    private List<MemberTerm> memberTermList = new ArrayList<>();
}
