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

    // 추가: Post Entity 객체를 받아서 PostDto 객체로 변환하는 기능
    public static PostDTO of(Post post) {
        PostDTO postDto = new PostDTO();
        postDto.setPNo(post.getPNo());
        postDto.setBNo(post.getBNo());
        postDto.setBName(post.getBName());
        postDto.setPTitle(post.getPTitle());
        postDto.setPContent(post.getPContent());

        return postDto;
    }
}