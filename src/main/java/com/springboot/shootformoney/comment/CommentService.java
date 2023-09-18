package com.springboot.shootformoney.comment;

import com.springboot.shootformoney.comment.CommentRepository;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.post.Post;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addComment(Integer mNo, Integer pNo, String content) {

        // memberService.findById(mNo) 와 postService.findById(pNo) 로부터 Member와 Post 객체를 가져오는 코드가 필요합니다.
        // 해당 코드는 실제 구현에 따라 달라질 수 있으므로 여기서는 생략하였습니다.

        Member member = new Member();
        Post post = new Post();

        Comment comment = new Comment();

        comment.setMember(member);
        comment.setPost(post);
        comment.setCContent(content);
        comment.setCCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }
}