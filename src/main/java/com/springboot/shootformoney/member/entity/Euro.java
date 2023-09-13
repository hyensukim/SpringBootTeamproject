package com.springboot.shootformoney.member.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
public class Euro {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mNo")
    private Member member;

    @Column(nullable = false)
    private Integer value;
}
