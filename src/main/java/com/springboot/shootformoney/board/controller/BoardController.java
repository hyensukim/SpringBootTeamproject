package com.springboot.shootformoney.board.controller;

import com.springboot.shootformoney.PageHandler;
import com.springboot.shootformoney.admin.service.postservice.PostFindService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@RestController
@Controller
@RequestMapping("admin/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final PostFindService postFindService;

    // 게시파 전체 조회
//    @GetMapping("/")
//    public ResponseEntity<List<BoardDto>> getAllBoards() {
//        List<BoardDto> boardDtos = boardService.getAllBoards();
//        return ResponseEntity.ok(boardDtos);
//    }


//    // 게시판 생성
//    @PostMapping("/")
//    public ResponseEntity<?> createBoard(@RequestBody BoardDto newBoardDto) {
//        Board newBoard = new Board();
//        newBoard.setBName(newBoardDto.getBName());
//        newBoard.setBPageNo(newBoardDto.getBPageNo());
//        newBoard.setBUnitNo(newBoardDto.getBUnitNo());
//        newBoard.setBIsFile(newBoardDto.isBIsFile());
//
//        try {
//            Long bNo = boardService.saveBoardInfo(newBoard);
//            return new ResponseEntity<>(bNo, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

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
    public String deleteBoard(@PathVariable Long bNo) {
        boardService.deleteBoard(bNo);
        return "redirect:/admin/board/boardList";
    }


    // 페이징 처리
    @GetMapping("/paging/{bNo}")
    public ResponseEntity<PageHandler> getBoards(@PathVariable Long bNo,
                                                 @RequestParam(defaultValue = "1") int bPageNo,
                                                 @RequestParam(defaultValue = "10") int bUnitNo) {

        return ResponseEntity.ok(boardService.getBoardsWithPaging(bNo, bPageNo, bUnitNo));
    }

//    @GetMapping("/boards/{bNo}")
//    public ResponseEntity<Page<BoardDto>> getBoards(@PathVariable Long bNo,
//                                                    @RequestParam(defaultValue = "1") int bPageNo,
//                                                    @RequestParam(defaultValue = "10") int bUnitNo) {
//
//        return ResponseEntity.ok(boardService.getBoardWithPaging(bNo, bPageNo, bUnitNo));
//    }


//    // 게시판 리스트 조회 페이지
//    @GetMapping("/boardList")
//    public String getAllBoards(Model model) {
//        List<Board> boards = boardService.getAllBoards();
//        model.addAttribute("boards", boards);
////        model.addAttribute("pageTitle", "게시판 관리");
//        return "admin/boardManagement";
//    }

    // 게시판 검색 기능
    @GetMapping("/boardList/search")
    public String searchBoards(@RequestParam(required = false) Long bNo,
                               @RequestParam(required = false) String bName,
                               @RequestParam(required = false) Boolean bIsFile,
                               Model model) {
        List<Board> boards = boardService.searchBoards(bNo, bName, bIsFile);
        model.addAttribute("boards", boards);
        return "admin/boardManagement";
    }

    // 각 행에 해당하는 게시글 개수 출력
    @GetMapping("/boardList")
    public String getAllBoards(Model model) {
        List<Board> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);

        Map<Long, Integer> postCountsMap = new HashMap<>();
        for (Board board : boards) {
            int postCount = boardService.getPostCountByBoardId(board.getBNo());
            postCountsMap.put(board.getBNo(), postCount);
        }
        model.addAttribute("postCounts", postCountsMap);
        model.addAttribute("pageTitle", "게시판 관리");

        return "admin/boardManagement"; // thymeleaf template 경로
    }

    // 게시판 생성 처리
    @PostMapping("/create")
    public String createBoard(@ModelAttribute BoardDto newBoardDto, RedirectAttributes redirectAttributes) {
        Board newBoard = new Board();
        newBoard.setBName(newBoardDto.getBName());
        newBoard.setBPageNo(newBoardDto.getBPageNo());
        newBoard.setBUnitNo(newBoardDto.getBUnitNo());
        newBoard.setBIsFile(newBoardDto.isBIsFile());

        try {
            Long bNo = boardService.saveNewboardInfo(newBoard);
            redirectAttributes.addFlashAttribute("successMessage", "게시판이 성공적으로 생성되었습니다.");
            return "redirect:/admin/board/boardList";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//            redirectAttributes.addFlashAttribute("errorMessage", "게시판 생성에 실패하셨습니다.");
            return "redirect:/admin/board/boardList";
        }
    }
}