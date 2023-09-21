package com.springboot.shootformoney.admin.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
public class ConfigForm {
    private String siteTitle = ""; // 사이트 명
    private String siteDescription = ""; // 사이트 설명
    private String cssJsVersion = ""+1; // css & JS 버전
    private String siteRule = ""; // 사이트 규칙 ex) 게시글 위반사항(욕설, 비속어, 도배 등)
}
