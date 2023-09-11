package com.springboot.shootformoney.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer c_no;

    @Column(nullable = false)
    private Integer m_no;

    @Column(nullable = false)
    private Integer p_no;

    @Column(nullable = false)
    private String c_content;

    @Column
    private LocalDateTime c_createAt;

    @Column
    private LocalDateTime c_updateAt;

}
