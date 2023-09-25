package com.springboot.shootformoney.comment.dto;

import com.springboot.shootformoney.comment.entity.Comment;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.post.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {

    private Long cNo;
    private String cContent;
    private Member member;
    private Post post;

    public Comment toEntity(){
        return Comment.builder()
                .cNo(cNo)
                .cContent(cContent)
                .member(member)
                .post(post)
                .build();
    }
}
