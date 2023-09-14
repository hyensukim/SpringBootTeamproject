package com.springboot.shootformoney.post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private Long pNo;

    private Long bNo;

    private String bName;

    @NotNull(message = "제목을 정해주세요")
    @Size(min = 1, max = 100, message = "제목 글자수 제한 오류")
    private String pTitle;

    @Size(min = 1, max = 3000, message = "게시글 글자수 제한 오류")
    private String pContent;

    public static PostDTO of(Post post) {
        PostDTO dto = new PostDTO();

        dto.setPNo(post.getPNo());
        dto.setBNo(post.getBoard().getBNo()); // 게시판 번호 설정. Board 필드가 없다면 이 부분 수정 필요.
        dto.setBName(post.getBoard().getBName()); // 게시판 이름 설정. Board 필드가 없다면 이 부분 수정 필요.
        dto.setPTitle(post.getPTitle());
        dto.setPContent(post.getPContent());

        return dto;
    }
}