package com.springboot.shootformoney.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    // 저장
    @Transactional
    public Long saveBoard(Board board){
        boardRepository.save(board);
        return board.getBNo();
    }

    // 삭제
    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findOne(id);
        if (board != null) {
            boardRepository.delete(board);
        } else {
            throw new IllegalArgumentException("해당 아이디의 게시판이 존재하지 않습니다");
        }
    }


}
