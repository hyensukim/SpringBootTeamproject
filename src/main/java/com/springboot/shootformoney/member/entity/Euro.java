package com.springboot.shootformoney.member.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
public class Euro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mNo")
    private Member member;

    @Column(nullable = false)
    private Integer value;
}
