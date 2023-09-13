package com.springboot.shootformoney.board.service;

import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시판 저장
    @Transactional
    public Long saveBoardInfo(Board board) {
        boardRepository.save(board);
        return board.getBNo();
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

//    // 게시판 조회
//    public List<Board> findAllBoards() {
//        return boardRepository.findAll();
//    }

    // 모든 게시판 조회 (페이지네이션 적용)
    // Pageable 객체를 파라미터로 받아서 해당 정보에 맞게 페이지네이션된 결과를 반환
    public Page<Board> findAllBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

}
