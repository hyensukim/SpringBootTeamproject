package com.springboot.shootformoney.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FindIdForm {

    @NotBlank(message = "이름을 입력하세요.")
    private String mName;
    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message="이메일 양식으로 입력하세요.")
    private String mEmail;

}
