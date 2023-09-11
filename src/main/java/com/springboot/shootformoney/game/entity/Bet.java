package com.springboot.shootformoney.game.entity;

import com.springboot.shootformoney.game.dto.data.Result;
import jakarta.persistence.*;

import java.time.LocalDateTime;

//유저의 베팅 목록을 조회하는 엔터티.
@Entity
@Table(name = "bet")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bt_no")
    private Long btNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "g_no")
    private Game game;

    //멤버ID FK로 받아오는 부분은 합칠 때 생각.
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="m_no")
//    private Member member;

    @Column(name = "bt_money", nullable = false)
    private Integer btMoney;

    @Column(name = "bt_result", nullable = false)
    @Enumerated(EnumType.STRING)
    private Result result;

    @Column(name = "bt_time")
    private LocalDateTime btTime;

//    최종배당률 칼럼은 일단 모두 만들어보고 넣을지말지 생각.
//    @Column(name = "bt_ratio")
//    private Double btRatio;
}
