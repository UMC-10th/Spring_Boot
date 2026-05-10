package com.umc.umc10th.domain.user.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "helps")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Help {

    @Id
    @Column(name = "key", length = 20, nullable = false)
    private String key;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
}