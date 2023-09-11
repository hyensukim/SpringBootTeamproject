package com.springboot.shootformoney.member;

import lombok.Getter;

@Getter
public enum MemberGrade {
    CONFERECE("컨퍼런스"),
    EUROPA("유로파"),
    CHAMPIONS("챔피언스");

    private String value;

    MemberGrade(String value) {
        this.value = value;
    }
}
