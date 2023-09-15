package com.springboot.shootformoney.board.controller;

import com.springboot.shootformoney.board.dto.BoardDto;
import com.springboot.shootformoney.board.service.BoardService;
import com.springboot.shootformoney.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
//@Controller
@RequestMapping("admin/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    
    // 게시파 전체 조회
    @GetMapping("/")
    public ResponseEntity<List<BoardDto>> getAllBoards() {
        List<BoardDto> boardDtos = boardService.getAllBoards();
        return ResponseEntity.ok(boardDtos);
    }


    // 게시판 생성
    @PostMapping("/")
    public ResponseEntity<?> createBoard(@RequestBody BoardDto newBoardDto) {
        Board newBoard = new Board();
        newBoard.setBName(newBoardDto.getBName());
        newBoard.setBPageNo(newBoardDto.getBPageNo());
        newBoard.setBUnitNo(newBoardDto.getBUnitNo());
        newBoard.setBIsFile(newBoardDto.isBIsFile());

        try {
            Long bNo = boardService.saveBoardInfo(newBoard);
            return new ResponseEntity<>(bNo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 게시판 수정 - 이름, 파일 첨부 여부
    @PutMapping("/{bNo}/update")
    public ResponseEntity<Void> updateBoardInfo(
            @PathVariable Long bNo,
            @RequestParam("newBName") String newBName,
            @RequestParam("newBIsFile") boolean newBIsFile) {
        try {
            boardService.updateBoardInfo(bNo, newBName, newBIsFile);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 게시판 삭제
    @DeleteMapping("/{bNo}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long bNo) {
        boardService.deleteBoard(bNo);
        return ResponseEntity.noContent().build();
    }
}