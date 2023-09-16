package com.springboot.shootformoney.game.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FullTime {
    @JsonProperty("home")
    private Byte home;
    @JsonProperty("away")
    private Byte away;
}
