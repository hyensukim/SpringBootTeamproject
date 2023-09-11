package com.springboot.shootformoney.member;

import lombok.Getter;

@Getter
public enum MemberRole {
    Member("멤버"),
    ADMIN("어드민");

    private String value;

    MemberRole(String value) {
        this.value = value;
    }

}
