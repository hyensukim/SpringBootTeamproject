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

//    // 게시판 조회
//    @GetMapping("/list")
//    public List<BoardDto> getAllBoards() {
//        List<Board> boards = boardService.findAllBoards();
//
//        // Entity 리스트를 Dto 리스트로 변환
//        List<BoardDto> boardDtos = boards.stream()
//                .map(BoardDto::fromEntity)
//                .collect(Collectors.toList());
//
//        return boardDtos;
//    }

    @GetMapping("/")
    public ResponseEntity<Page<Board>> getBoards(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size){

        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(boardService.findAllBoards(pageRequest));
    }

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

    @DeleteMapping("/{bNo}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long bNo) {
        boardService.deleteBoard(bNo);
        return ResponseEntity.noContent().build();
    }
}