package com.springboot.shootformoney.member.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Entity @Data
@Getter
public class Euro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mNo")
    private Member member;

    @Column(nullable = false)
    private Integer value = 100 * 10000;
}
