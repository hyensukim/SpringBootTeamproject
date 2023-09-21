package com.springboot.shootformoney.board.controller;

import com.springboot.shootformoney.admin.service.postservice.PostFindService;
import com.springboot.shootformoney.board.dto.BoardDto;
import com.springboot.shootformoney.board.service.BoardService;
import com.springboot.shootformoney.board.entity.Board;
import lombok.RequiredArgsConstructor;
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

    // 게시판 전체 조회
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

    // 게시판 생성
    @PostMapping("/create")
    public String createBoard(@ModelAttribute BoardDto newBoardDto, RedirectAttributes redirectAttributes) {
        Board newBoard = new Board();
        newBoard.setBName(newBoardDto.getBName());
        newBoard.setBPageNo(newBoardDto.getBPageNo());
        newBoard.setBUnitNo(newBoardDto.getBUnitNo());
//        newBoard.setBIsFile(newBoardDto.isBIsFile());

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

    // 게시판 수정
    @PostMapping("/update/{bNo}")
    public String updateBoard(@PathVariable Long bNo,
                              @RequestParam("newBName") String newBName,
                              @RequestParam("newBUniNo") int newBUnitNo,
                              @RequestParam("newBPageNo") int newBPageNo) {
        boardService.updateBoardInfo(bNo, newBName, newBUnitNo, newBPageNo);
        return "redirect:/admin/board/boardList";
    }


    // 게시판 삭제
    @DeleteMapping("/{bNo}")
    public String deleteBoard(@PathVariable Long bNo) {
        boardService.deleteBoard(bNo);
        return "redirect:/admin/board/boardList";
    }

//    // 페이징 처리
//    @GetMapping("/paging/{bNo}")
//    public ResponseEntity<PageHandler> getBoards(@PathVariable Long bNo,
//                                                 @RequestParam(defaultValue = "1") int bPageNo,
//                                                 @RequestParam(defaultValue = "10") int bUnitNo) {
//
//        return ResponseEntity.ok(boardService.getBoardsWithPaging(bNo, bPageNo, bUnitNo));
//    }

//    @GetMapping("/boards/{bNo}")
//    public ResponseEntity<Page<BoardDto>> getBoards(@PathVariable Long bNo,
//                                                    @RequestParam(defaultValue = "1") int bPageNo,
//                                                    @RequestParam(defaultValue = "10") int bUnitNo) {
//
//        return ResponseEntity.ok(boardService.getBoardWithPaging(bNo, bPageNo, bUnitNo));
//    }



}