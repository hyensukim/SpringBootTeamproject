package com.springboot.shootformoney.admin.dto;

import lombok.Data;

@Data
public class AdminSearchInfo {

    private int page = 1;
    private int pageSize = 10;

    private String sOpt; // 검색 조건
    private String sKey; // 검색 키워드(유저 입력)

    //    private String category; // 검색 조건
//    private String query;    // 검색 키워드 (유저 입력)

}
