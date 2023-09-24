package com.springboot.shootformoney.bet.entity;

import com.springboot.shootformoney.game.dto.data.Result;
import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

//유저들의 베팅 목록을 담은 엔터티.
@Entity
@Table(name = "bet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bt_no")
    private Long btNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "g_no")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="m_no")
    private Member member;

    @Column(name = "bt_money", nullable = false)
    private Integer btMoney;

    //회원의 예상 결과를 담는 칼럼.
    @Column(name = "bt_expect", nullable = false)
    @Enumerated(EnumType.STRING)
    private Result expect;

    @Column(name = "bt_time")
    private LocalDateTime btTime;

    //최종 배당률 칼럼은 일단 모두 만들어 보고 넣을지 말지 생각.
    @Column(name = "bt_ratio")
    private Double btRatio = 1.0; //기본값 설정(경기 결과 나오면 update)

    @Column(name = "bt_end_paid")
    private Byte endPaid = 0;    //0은 false(아직 정산 후 지급 안 됨.), 1은 true(정산 후 지급됨)를 뜻한다.

    //배팅 적중 여부 표시. 1은 적중, 0은 적중 실패, 기본값 0
    @Column(name = "bt_correct")
    private Byte correct = 0;
}
