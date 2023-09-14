package com.springboot.shootformoney.member.dto;

import lombok.Data;

@Data
public class LoginForm {

    private String mId;

    private String mPassword;

    private boolean saveId; // 아이디 저장
}
