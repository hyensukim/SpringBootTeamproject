package com.springboot.shootformoney.game.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.shootformoney.game.dto.data.FullTime;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScoreDto {
    @JsonProperty("fullTime")
    private FullTime fullTime;
}
