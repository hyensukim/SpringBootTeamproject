package com.springboot.shootformoney.member.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class FindIdForm {

    private String mName;
    private String mEmail;

}
