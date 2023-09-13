package com.springboot.shootformoney.board.dto;

import com.springboot.shootformoney.board.entity.Board;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

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

    // fromEntity() 메소드를 통해 Board Entity 객체를 받아서 BoardDTO 객체로 변환하는 기능
    public static BoardDto fromEntity(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBNo(board.getBNo());
        boardDto.setBName(board.getBName());
        boardDto.setBPageNo(board.getBPageNo());
        boardDto.setBUnitNo(board.getBUnitNo());
        boardDto.setBIsFile(board.isBIsFile());

        return boardDto;
    }


}
