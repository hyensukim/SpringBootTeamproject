package com.springboot.shootformoney.comment;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_no")
    private Integer cNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_no", nullable = false)
    private Post post;

    @Column(name="c_content", nullable=false, length=1000)
    private String cContent;

    @Column(name="c_createdAt")
    private LocalDateTime cCreatedAt;

    @Column(name="c_updatedAt")
    private LocalDateTime cUpdatedAt;

    // Getter, Setter and other methods...
    }
