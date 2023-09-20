package com.springboot.shootformoney.member.dto;

import lombok.Data;

@Data
public class PageInfo {

    private int page = 1;

    private int pageSize = 10;

}
