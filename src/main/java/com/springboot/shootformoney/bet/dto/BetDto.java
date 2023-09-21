package com.springboot.shootformoney.bet.dto;


import com.springboot.shootformoney.game.dto.data.Result;
import com.springboot.shootformoney.game.entity.Game;
import lombok.Data;

import java.time.LocalDateTime;

//bet()메서드를 활용하기 위한 DTO 클래스.
@Data
public class BetDto {

    private Long gNo;
    private Long mNo;
    private Integer matchId;
    private Integer btMoney;
    private String expect;
}
