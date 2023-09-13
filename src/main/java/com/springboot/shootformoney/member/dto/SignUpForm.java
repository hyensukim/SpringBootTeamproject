package com.springboot.shootformoney.member.dto;


    import jakarta.validation.constraints.*;
    import lombok.Data;

    @Data
    public class SignUpForm {

        @NotBlank
        @Size(min=4, max=16)
        private String mId;

        @NotBlank
        @Size(min=8, max=16)
        private String mPassword;

        @NotBlank
        private String mPasswordCheck; // 비밀번호 확인

        @NotBlank
        @Size(min=2, max=20)
        private String mName;

        @NotBlank
        private String mPhone;

        @Email
        private String mEmail;

        @NotBlank
        @Size(min=4, max=16)
        private String mNickName;
    }
