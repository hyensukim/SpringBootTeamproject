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

    private LocalDateTime loginDate;

    private LocalDateTime lastLoginDate;

}