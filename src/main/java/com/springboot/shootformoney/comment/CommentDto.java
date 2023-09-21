package com.springboot.shootformoney.comment;

import com.springboot.shootformoney.post.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {

    private Long cNo;
    private Long mNo; // 회원 번호
    private Long pNo; // 게시글 번호
    private String cContent;
    private String mNickName; // 회원 닉네임
    private String pTitle; // 게시글 제목
    private String pContent; // 게시글 내용
    private LocalDateTime cCreatedAt;
    private LocalDateTime cUpdatedAt;


}