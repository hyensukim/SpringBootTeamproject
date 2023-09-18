package com.springboot.shootformoney.member.dto;

import lombok.Data;

@Data
public class BoardSearch {

    private int page = 1;

    private int pageSize = 20;

}
