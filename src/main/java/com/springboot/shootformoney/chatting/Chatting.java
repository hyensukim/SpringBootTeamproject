package com.springboot.shootformoney.chatting;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Chatting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "g_no")
    private Game gno;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_no")
    private Member mno;

    private String ch_content;

    private LocalDateTime ch_createdAt;

    // getters and setters...
}

//경기 번호를 pk로 하기