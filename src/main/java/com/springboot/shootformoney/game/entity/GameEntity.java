package com.springboot.shootformoney.game.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/*경기 데이터 엔터티
* Author: Hyedokal(https://www.github.com/Hyedokal)
 */
@Entity
@Table(name = "game")
public class GameEntity {

    //PK 설정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long g_no;

    @Column(nullable = false)
    private String g_home_team;

    @Column(nullable = false)
    private String g_away_team;

    @Column(nullable = false)
    private LocalDateTime g_start_time;

    //end time은 경기마다 다르므로 굳이 저장하지 않을 예정
    private String g_result;

    private Byte g_home_score;

    private Byte g_away_score;
}
