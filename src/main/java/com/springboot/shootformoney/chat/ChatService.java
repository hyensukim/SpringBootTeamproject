package com.springboot.shootformoney.chat;

import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Transactional
    public Chat addComment(Long memberId, Long gameId, String content) {
        // 회원과 경기 정보는 실제 상황에서는 다른 서비스나 리포지토리로부터 조회해야 합니다.
        Member member = new Member();
        Game game = new Game();

        // 새 댓글 객체 생성
        Chat comment = new Chat();
        comment.setMember(member);
        comment.setGame(game);
        comment.setChContent(content);

        // 현재 시간 설정
        comment.setChCreatedAt(LocalDateTime.now());

        // DB에 저장 후 결과 반환
        return chatRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Chat> getCommentsByMemberId(Integer mNo) {
        // 해당 회원이 작성한 모든 댓글 반환
        return chatRepository.findByMember_MNo(mNo);
    }

    @Transactional(readOnly = true)
    public Optional<Chat> getCommentById(Long id) {
        // ID로 댓글 조회 후 결과 반환
        return chatRepository.findById(id);
    }
}
