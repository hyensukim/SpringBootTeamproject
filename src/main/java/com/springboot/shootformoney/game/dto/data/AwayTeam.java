package com.springboot.shootformoney.game.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AwayTeam {
    @JsonProperty("name")
    private String name;
}
