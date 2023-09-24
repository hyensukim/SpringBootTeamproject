package com.springboot.shootformoney.post;

import lombok.Data;

@Data
public class PostSearchInfo {
    
    private Integer page = 1;
    private Integer pageSize = 10;
    private String sOpt; // 검색 종류(제목, 내용, 제목+내용)
    private String sKey; // 사용자가 검색하는 키워드
    private Long bNo;

}
