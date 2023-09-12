package com.springboot.shootformoney.game.entity;

import com.springboot.shootformoney.game.dto.data.Result;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/*경기 데이터 엔터티
* Author: Hyedokal(https://www.github.com/Hyedokal)
 */
@Entity
@Table(name = "game")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Game {

    //PK 설정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long g_no;
    @Column(name = "g_league", nullable = false)
    private String gLeague;
    @Column(name="g_home_team", nullable = false)
    private String gHomeTeam;

    @Column(name="g_away_team", nullable = false)
    private String gAwayTeam;

    @Column(name="g_start_time", nullable = false)
    private String gStartTime;

    //end time은 경기마다 다르므로 굳이 저장하지 않을 예정

    @Transient //homeScore, awayScore의 값을 기반으로 계산하여 사용한다.
    @Enumerated(EnumType.STRING)
    @Column(name = "g_Result")
    private Result result;

    public Result getResult() {
        if(gHomeScore == null || gAwayScore == null){
            return null;
        } else if(gHomeScore > gAwayScore){
            return Result.WIN;
        } else if(gHomeScore.equals(gAwayScore)){
            return Result.DRAW;
        } else{
            return Result.LOSE;
        }
    }

    @Column(name="g_home_score")
    private Byte gHomeScore;

    @Column(name="g_away_score")
    private Byte gAwayScore;


}
