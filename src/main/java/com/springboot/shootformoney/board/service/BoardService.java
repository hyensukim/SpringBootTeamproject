package com.springboot.shootformoney.board.service;

import com.springboot.shootformoney.board.dto.BoardDto;
import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시판 저장
    @Transactional
    public Long saveBoardInfo(Board board) {
        Board existingBoard = boardRepository.findBybName(board.getBName());
        if (existingBoard != null) {
            throw new IllegalArgumentException("이미 존재하는 게시판입니다.");
        }

        boardRepository.save(board);
        return board.getBNo();
    }
    
    // 게시판 전체 조회
    public List<BoardDto> getAllBoards() {
        List<Board> boards = boardRepository.findAll();

        // Entity 리스트를 Dto 리스트로 변환
        List<BoardDto> boardDtos = boards.stream()
                .map(board -> BoardDto.fromEntity(board))
                .collect(Collectors.toList());

        return boardDtos;
    }

    // 게시판 수정 - 이름, 파일 첨부 여부
    @Transactional
    public void updateBoardInfo(Long bNo, String newBName, boolean newBIsFile) {
        Board board = boardRepository.findBybNo(bNo);

        if (boardRepository.existsBybName(newBName)) {  // 중복 여부 판단
            throw new IllegalArgumentException("이미 존재하는 게시판 이름입니다.");
        } else if (board != null) {
            board.setBName(newBName);
            board.setBIsFile(newBIsFile);
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

}
