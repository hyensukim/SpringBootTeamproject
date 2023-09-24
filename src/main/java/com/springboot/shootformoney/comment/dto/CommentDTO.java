package com.springboot.shootformoney.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    private Long cNo; // 댓글 번호

    private String cContent;  // 댓글 본문

    private String mNickName; // 회원 별명

    private Long pNo; // 게시글 번호

}