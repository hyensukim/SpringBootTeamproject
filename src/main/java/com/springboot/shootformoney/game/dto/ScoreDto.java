package com.springboot.shootformoney.game.dto;

import com.springboot.shootformoney.game.dto.data.FullTime;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScoreDto {
    private FullTime fullTime;
}
