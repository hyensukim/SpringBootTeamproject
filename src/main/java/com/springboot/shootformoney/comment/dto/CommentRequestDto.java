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
    @NotBlank(message="댓글을 작성해주세요.")
    @Size(max = 400, message = "400자 미만으로 작성해주세요.")
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
