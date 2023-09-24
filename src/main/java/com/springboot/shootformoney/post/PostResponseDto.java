package com.springboot.shootformoney.post;

import com.springboot.shootformoney.comment.dto.CommentResponseDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시글 정보를 리턴할 응답(Response) 클래스
 * Entity 클래스를 생성자 파라미터로 받아 데이터를 Dto로 변환하여 응답
 * 별도의 전달 객체를 활용해 연관관계를 맺은 엔티티간의 무한참조를 방지
 */
@Getter
public class PostResponseDto {
    private final Long pNo;
    private final String pTitle;
    private final String mNickName;
    private final String pContent;
    private final Long view;
    private final Long mNo;
    private final List<CommentResponseDto> comments;

    public PostResponseDto(Post post){
        this.pNo = post.getPNo();
        this.pTitle = post.getPTitle();
        this.pContent = post.getPContent();
        this.mNickName = post.getMember().getMNickName();
        this.view = post.getView();
        this.mNo = post.getMember().getMNo();
        this.comments = post.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
