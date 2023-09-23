package com.springboot.shootformoney.comment.service;

import com.springboot.shootformoney.comment.dto.CommentRequestDto;
import com.springboot.shootformoney.comment.entity.Comment;
import com.springboot.shootformoney.comment.repository.CommentRepository2;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import com.springboot.shootformoney.post.Post;
import com.springboot.shootformoney.post.PostRepository;
import com.springboot.shootformoney.post.repository.PostRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository2 commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepositoryInterface postRepository;

    //CREATE
//    @Transactional
//    public Long save(Comment comment) {
//        commentRepository.save(comment);
//        return comment.getCNo();
//    } // 댓글 저장

    @Transactional
    public Long commentSave(String mNickName, Long pNo, CommentRequestDto dto){
        Member member = memberRepository.findBymNickName(mNickName);
        Post post = postRepository.findById(pNo).orElseThrow(()->
                new IllegalArgumentException("댓글 쓰기 실패 : 해당 게시글이 존재하지 않습니다." + pNo));
        dto.setMember(member);
        dto.setPost(post);

        Comment comment = dto.toEntity();
        commentRepository.save(comment);

        return dto.getCNo();
    }

    @Transactional(readOnly = true)
    public Comment findOne(Long id) {
//        return commentRepository.findOne(id);
        return null;
    } // 댓글 단일 조회

    @Transactional(readOnly = true)
    public List<Comment> findAll() {
        return commentRepository.findAll();
    } // 댓글 전체 조회

    @Transactional
    public void delete(Long id) {
        Comment comment = findOne(id);
        if (comment != null) {
            commentRepository.delete(comment);
        } // 댓글 지우기 // 회원 로그인된 세션 정보 불러와서 일치하면 지울수 있게 하기

    }
}