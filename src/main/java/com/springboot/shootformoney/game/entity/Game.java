package com.springboot.shootformoney.game.entity;

import com.springboot.shootformoney.game.dto.data.Result;
import jakarta.persistence.*;
import lombok.*;

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
@Getter
@Setter
public class Game {

    //PK 설정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gNo;

    //API에서 제공하는 경기마다의 고유한 ID. (table update를 위해 추가)
    @Column(name = "match_id", nullable = false, unique = true)
    private Integer matchId;

    @Column(name = "g_league", nullable = false)
    private String gLeague;
    @Column(name="g_home_team", nullable = false)
    private String gHomeTeam;

    @Column(name="g_away_team", nullable = false)
    private String gAwayTeam;

    @Column(name="g_start_time", nullable = false)
    private String gStartTime;

    //end time은 경기마다 다르므로 굳이 저장하지 않을 예정

    @Enumerated(EnumType.STRING)
    @Column(name = "g_Result")
    private Result result = Result.NN; //필드 선언 시 디폴트 값으로 초기화.


    /*
    * Entity가 저장되거나 업데이트될 때마다 호출되어
    * gHomeScore와 gAwayScore를 비교하고 그 결과에 따라 result 값을 설정한다.
     */
    @PrePersist
    @PreUpdate
    public void calcResult() {
        if(gHomeScore == null || gAwayScore == null){
            this.result = Result.NN;
        } else if(gHomeScore > gAwayScore){
            this.result = Result.WIN;
        } else if(gHomeScore.equals(gAwayScore)){
            this.result = Result.DRAW;
        } else{
            this.result = Result.LOSE;
        }
    }

    @Column(name="g_home_score")
    private Byte gHomeScore;

    @Column(name="g_away_score")
    private Byte gAwayScore;


}
