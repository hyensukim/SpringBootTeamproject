package com.springboot.shootformoney.member.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity @Data
public class LoginData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="l_no")
    private Long lNo;

//    @OneToOne(fetch=FetchType.LAZY)
//    @JoinColumn
//    private Member member;

    private LocalDateTime loginDate;

    private LocalDateTime lastLoginDate;

}