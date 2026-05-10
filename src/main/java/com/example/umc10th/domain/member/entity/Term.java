package com.example.umc10th.domain.member.entity;

import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import com.example.umc10th.domain.member.enums.TermName;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "term")
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id", nullable = false)
    private Long id;

    @Builder.Default
    @Column(name = "name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TermName name = TermName.NONE;

    public static Term of(TermName name) {
        return Term.builder()
                .name(name == null ? TermName.NONE : name)
                .build();
    }

    @OneToMany(mappedBy = "term", fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberTerm> memberTerms = new ArrayList<>();
}
