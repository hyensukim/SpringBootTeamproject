package com.springboot.shootformoney.post;

import com.springboot.shootformoney.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long pNo;
    private Long bNo;
    private String bName;
    private Long view;
    private String mId; //수정
    private String mNickName; //수정
    private String pTitle;
    private String pContent;

    public static PostDTO of(Post post) {
        PostDTO dto = new PostDTO();
        dto.setPNo(post.getPNo());
        dto.setBNo(post.getBoard().getBNo()); // 게시판 번호 설정. Board 필드가 없다면 이 부분 수정 필요.
        dto.setView(post.getView());
        dto.setBName(post.getBoard().getBName()); // 게시판 이름 설정. Board 필드가 없다면 이 부분 수정 필요.
        dto.setPTitle(post.getPTitle());
        dto.setPContent(post.getPContent());

        // Member 정보 설정 부분 수정: 회원 ID와 별명 설정(수정)
        if (post.getMember() != null) {
            dto.setMId(post.getMember().getMId());
            dto.setMNickName(post.getMember().getMNickName());
        }
        return dto;
    }

}