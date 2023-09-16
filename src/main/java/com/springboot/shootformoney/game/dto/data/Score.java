package com.springboot.shootformoney.game.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Score {
    @JsonProperty("fullTime")
    private FullTime fullTime;
}
