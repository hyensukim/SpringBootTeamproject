package com.springboot.shootformoney.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConfigDto {

    private String code;    // PK

    private String value;   // json 형태의 데이터

    public ConfigDto() {
        // Default constructor
    }

    public ConfigDto(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
