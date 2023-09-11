package com.springboot.shootformoney.game.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Competition {
    @JsonProperty("name")
    private String name;
}
