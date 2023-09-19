package com.springboot.shootformoney.board.controller;

import com.springboot.shootformoney.board.PageHandler;
import com.springboot.shootformoney.board.dto.BoardDto;
import com.springboot.shootformoney.board.service.BoardService;
import com.springboot.shootformoney.board.entity.Board;
import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Controller
@RequestMapping("admin/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    
    // 게시판 전체 조회
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

    // 게시판 수정 - 이름, 파일 첨부 여부, 게시판 게시글 수, 게시판 페이지 수
    @PutMapping("/update/{bNo}")
    public ResponseEntity<Void> updateBoardInfo(
            @PathVariable Long bNo, // 게시판 번호로 조회
            @RequestParam("newBName") String newBName,  // 게시판 이름
            @RequestParam("newBIsFile") boolean newBIsFile, // 게시판 파일 첨부 여부
            @RequestParam("newBUnitNo") int newBUnitNo, // 게시판 게시글 수
            @RequestParam("newBPageNo") int newBPageNo) { // 게시판 페이지 수
        try {
            boardService.updateBoardInfo(bNo, newBName, newBIsFile, newBUnitNo, newBPageNo);
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


//     페이징 처리
//    @GetMapping("/paging/{bNo}")
//    public ResponseEntity<PageHandler> getBoards(@PathVariable Long bNo,
//                                                 @RequestParam(defaultValue = "1") int bPageNo,
//                                                 @RequestParam(defaultValue = "10") int bUnitNo) {
//
//        return ResponseEntity.ok(boardService.getBoardsWithPaging(bNo, bPageNo, bUnitNo));
//    }

    // 페이징 처리
    @GetMapping("/paging/{bNo}")
    public String getBoards(Model model,
                            @PathVariable Long bNo,
                            @RequestParam(defaultValue = "1") int bPageNo,
                            @RequestParam(defaultValue = "10") int bUnitNo) {

        PageHandler pageHandler = boardService.getBoardsWithPaging(bNo, bPageNo, bUnitNo);
        List<BoardDto> boards = boardService.getAllBoards();

        model.addAttribute("boards", boards);
        model.addAttribute("pageHandler", pageHandler);

        return "post";
    }

//    @GetMapping("/boards/{bNo}")
//    public ResponseEntity<Page<BoardDto>> getBoards(@PathVariable Long bNo,
//                                                    @RequestParam(defaultValue = "1") int bPageNo,
//                                                    @RequestParam(defaultValue = "10") int bUnitNo) {
//
//        return ResponseEntity.ok(boardService.getBoardWithPaging(bNo, bPageNo, bUnitNo));
//    }
}