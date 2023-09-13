package com.springboot.shootformoney.game.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Match {
    @JsonProperty("id")
    private Integer matchId;

    @JsonProperty("competition")
    private Competition competition;

    @JsonProperty("utcDate")
    private String utcDate;

    @JsonProperty("homeTeam")
    private HomeTeam homeTeam;

    @JsonProperty("awayTeam")
    private AwayTeam awayTeam;

    @JsonProperty("score")
    private Score score;
}
