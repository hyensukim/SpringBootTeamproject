package com.springboot.shootformoney.chat;

import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="m_no")
    private Member member; // 회원

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="g_no")
    private Game game; // 경기

    @Column(name = "ch_content", length = 100, nullable = false)
    private String chContent; // 내용

    @Column(name = "ch_createdAt", nullable = false)
    private LocalDateTime chCreatedAt; // 작성시간

    /* Constructors, Getters and Setters */
}
