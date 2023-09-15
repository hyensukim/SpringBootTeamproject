package com.springboot.shootformoney.chatting;

import com.springboot.shootformoney.comment.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChattingService {

    private final MemberRepository memberRepository;
    private final GameRepository gameRepository;
    private final ChattingRepository chattingRepository;

    @Autowired
    public ChattingService(MemberRepository memberRepository,
                          GameRepository gameRepository,
                          ChattingRepository chattingRepository) {
        this.memberRepository = memberRepository;
        this.gameRepository = gameRepository;
        this.chattingRepository = chattingRepository;
    }

    public void addComment(Long userId, Long matchId, String message) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        Game game = gameRepository.findById(matchId).orElseThrow(() -> new IllegalArgumentException("Invalid match Id:" + matchId));

        Chatting chatting = new Chatting();
        chatting.setMember(member);
        chatting.setGame(game);
        chatting.setCh_content(ch_content);

        // 현재 시간 설정
        LocalDateTime now = LocalDateTime.now();
        chatting.setChattingTime(now);

        // 새로운 댓글 저장
        chattingRepository.save(ch_content);
    }

    public List<Chatting> getCommentsForMatch(Long matchId) {
        Game game = gameRepository.findById(matchId).orElseThrow(() -> new IllegalArgumentException("Invalid match Id:" + id));

        return chattingRepository.findByMatchOrderByCommentTimeAsc(game);
    }

    public List<Chatting> getNewCommentsForMatch(Long matchId, LocalDateTime since) {
        Game game = gameRepository.findById(matchId).orElseThrow(() -> new IllegalArgumentException("Invalid game Id:" + id));

        return	chattingRepository.findByMatchAndCommentTimeAfterOrderByCommentTimeAsc(game, since);
    }
}
