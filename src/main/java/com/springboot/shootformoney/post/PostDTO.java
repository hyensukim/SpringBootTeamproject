package com.springboot.shootformoney.post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private Long pNo;

    @NotNull(message = "제목을 정해주세요")
    @Size(min = 1, max = 100, message = "제목 글자수 제한 오류")
    private String pTitle;

    @Size(min = 1, max = 3000, message = "게시글 글자수 제한 오류")
    private String pContent;
}