package com.springboot.shootformoney.member.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpForm {

    @NotBlank
    @Min(4) @Max(16)
    private String mId;
    
    @NotBlank
    @Min(8) @Max(16)
    private String mPassword;

    @NotBlank
    private String mPasswordCheck; // 비밀번호 확인 

    @NotBlank
    @Min(2) @Max(20)
    private String mName;

    @NotBlank
    @Max(11)
    private String mPhone;

    @Email
    private String mEmail;

    @NotBlank
    @Min(4) @Max(16)
    private String mNickName;
}
