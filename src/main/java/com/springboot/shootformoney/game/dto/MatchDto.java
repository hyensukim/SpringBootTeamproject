package com.springboot.shootformoney.game.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.shootformoney.game.dto.data.Score;
import lombok.*;

@Data
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchDto {
    @JsonProperty("score")
    private Score score;
}
