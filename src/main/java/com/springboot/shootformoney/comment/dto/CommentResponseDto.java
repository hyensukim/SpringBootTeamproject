package com.springboot.shootformoney.comment.dto;

import com.springboot.shootformoney.comment.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    private Long id;
    private String comment;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
    private String nickname;
    private Long pNo;

/* Entity -> Dto*/
    public CommentResponseDto(Comment comment) {
        this.id = comment.getCNo();
        this.comment = comment.getCContent();
//        this.createdAt = comment.getCreatedAt();
//        this.updatedDate = comment.getModifiedDate();
        this.nickname = comment.getMember().getMNickName();
        this.pNo = comment.getPost().getPNo();
    }
}
