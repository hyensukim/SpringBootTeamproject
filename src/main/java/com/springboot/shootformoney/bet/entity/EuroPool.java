package com.springboot.shootformoney.bet.entity;

import com.springboot.shootformoney.game.entity.Game;
import jakarta.persistence.*;
import lombok.*;

/*
* 각 경기별 걸린 유로 합을 실시간으로 update하여 저장하는 엔터티.
* Author: Hyedokal(https://www.github.com/Hyedokal)
*/
@Entity
@Table(name = "euroPool")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EuroPool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ep_no")
    private Long epNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "g_no")
    private Game game;

    @Column(name = "win_euro", nullable = false, columnDefinition = "int default 1")
    private Integer winEuro;

    @Column(name = "draw_euro", nullable = false, columnDefinition = "int default 1")
    private Integer drawEuro;

    @Column(name = "lose_euro", nullable = false, columnDefinition = "int default 1")
    private Integer loseEuro;
}
