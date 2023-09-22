package com.springboot.shootformoney.board.service;

import com.springboot.shootformoney.PageHandler;
import com.springboot.shootformoney.board.dto.BoardDto;
import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시판 수정 - 이름, 파일 첨부 여부, 게시판 게시글 수, 게시판 페이지 수
//    @Transactional
//    public void updateBoardInfo(Long bNo, String newBName, int newBUnitNo, int newBPageNo) {
//        Board board = boardRepository.findBybNo(bNo);
//
//        if (boardRepository.existsBybName(newBName)) {  // 중복 여부 판단
//            throw new IllegalArgumentException("이미 존재하는 게시판 이름입니다.");
//        } else if (board != null) {
//            board.setBName(newBName); // 게시판 이름
//            board.setBUnitNo(newBUnitNo); // 게시판 게시글 수
//            board.setBPageNo(newBPageNo); // 게시판 페이지 수
//            boardRepository.save(board);
//        } else { // 존재 유무 판단
//            throw new IllegalArgumentException("해당 게시판이 존재하지 않습니다.");
//        }
//    }

    @Transactional
    public void updateBoardInfo(Long bNo, String newBName, int newBUnitNo, int newBPageNo) {
        Board board = boardRepository.findBybNo(bNo);

        if (boardRepository.existsBybName(newBName) && !newBName.equals(board.getBName())) {  // 중복 여부 판단
            throw new IllegalArgumentException("이미 존재하는 게시판 이름입니다.");
        } else if (board != null) {
            if (!newBName.equals(board.getBName())) {
                board.setBName(newBName); // 게시판 이름
            }
            board.setBUnitNo(newBUnitNo); // 게시판 게시글 수
            board.setBPageNo(newBPageNo); // 게시판 페이지 수
            boardRepository.save(board);
        } else { // 존재 유무 판단
            throw new IllegalArgumentException("해당 게시판이 존재하지 않습니다.");
        }
    }


    // 게시판 삭제
    public void deleteBoard(Long bNo) {
        Board board = boardRepository.findBybNo(bNo);
        if (board != null) {
            boardRepository.delete(board);
        } else {
            throw new IllegalArgumentException("해당 게시판이 존재하지 않습니다");
        }
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public int getPostCountByBoardId(Long bNo) {
        return boardRepository.getPostCountByBoardId(bNo);
    }

    // 게시판 생성
    public Long saveNewboardInfo(Board newBoard) throws IllegalArgumentException {
        // 게시판 이름 중복 체크
        Optional<Board> existingBoard = boardRepository.findBybName(newBoard.getBName());
        if (existingBoard.isPresent()) {
            throw new IllegalArgumentException("게시판 이름이 이미 존재합니다.");
        }

        // DB에 게시판 정보 저장
        Board savedBoard = boardRepository.save(newBoard);

        // 저장된 게시판의 ID 반환
        return savedBoard.getBNo();
    }

}
