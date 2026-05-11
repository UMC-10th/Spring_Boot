package com.example.Spring_Boot.domain.user.entity.mapping;

import com.example.Spring_Boot.domain.user.entity.Term;
import com.example.Spring_Boot.domain.user.entity.User;
import com.example.Spring_Boot.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_term")
public class UserTerm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_agreed", nullable = false)
    private Boolean isAgreed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id")
    private Term term;
}