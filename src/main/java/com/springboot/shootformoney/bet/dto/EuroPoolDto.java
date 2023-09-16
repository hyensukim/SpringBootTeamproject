package com.springboot.shootformoney.bet.dto;

import lombok.Data;

@Data
public class EuroPoolDto {
    public Long gNo;
    public Integer winEuro;
    public Integer drawEuro;
    public Integer loseEuro;
}
