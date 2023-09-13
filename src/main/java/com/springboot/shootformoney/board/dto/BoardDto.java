package com.springboot.shootformoney.board.dto;

import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.post.PostDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BoardDto {

    @NotBlank
    private Long bNo;

    @NotBlank
    @Max(100)
    private String bName; // 게시글 명(카테고리 명)

    @NotBlank
    private int bPageNo; // 페이지 수(하단), 기본 값 : 10

    @NotBlank
    private int bUnitNo; // 리스트별 게시글 개수, 기본 값 : 15

    @NotBlank
    private boolean bIsFile; // 파일 첨부 여부, 기본 값 : false

    // 기본 값 설정
    public BoardDto() {
        this.bPageNo = 10;
        this.bUnitNo = 15;
        this.bIsFile = false;
    }

    public BoardDto(Long bNo, String bName, int bPageNo, int bUnitNo,
                    boolean bIsFile, List<PostDTO> posts) {
        this.bNo = bNo;
        this.bName = bName;
        this.bPageNo = bPageNo;
        this.bUnitNo = bUnitNo;
        this.bIsFile = bIsFile;
        this.posts = posts;
    }

    private List<PostDTO> posts;

    public static BoardDto fromEntity(Board board) {
        return new BoardDto(
                board.getBNo(),
                board.getBName(),
                board.getBPageNo(),
                board.getBUnitNo(),
                board.isBIsFile(),
                board.getPosts().stream().map(PostDTO::of).collect(Collectors.toList())
        );
    }

}
