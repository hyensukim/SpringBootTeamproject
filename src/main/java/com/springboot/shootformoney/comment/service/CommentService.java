package com.springboot.shootformoney.comment.service;

import com.querydsl.core.BooleanBuilder;
import com.springboot.shootformoney.comment.dto.CommentRequestDto;
import com.springboot.shootformoney.comment.entity.Comment;
import com.springboot.shootformoney.comment.entity.QComment;
import com.springboot.shootformoney.comment.repository.CommentRepository;
import com.springboot.shootformoney.member.utils.MemberUtil;
import com.springboot.shootformoney.post.Post;
import com.springboot.shootformoney.post.PostRepository;
import com.springboot.shootformoney.post.repository.PostRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.data.domain.Sort.by;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepositoryInterface postRepository;
    private final MemberUtil memberUtil;

    // 생성
    @Transactional
    public void commentSave(CommentRequestDto dto, Long pNo){

        Optional<Post> op = postRepository.findById(pNo);
        Post post = null;
        if(op.isPresent()){post = op.get();}
        dto.setMember(memberUtil.getEntity());
        dto.setPost(post);
        Comment comment = dto.toEntity();
        commentRepository.save(comment);
    }

    // 댓글 전체 조회
    @Transactional(readOnly = true)
    public List<Comment> getList (Long pNo) {
        QComment comment = QComment.comment;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(comment.post.pNo.eq(pNo));
        List<Comment> commentList = new ArrayList<>();
        for (Comment value : commentRepository.findAll(andBuilder)) {
            commentList.add(value);
        }
        return commentList;
    }

    // 삭제
    @Transactional
    public Long delete(Long cNo) {
        Optional<Comment> oc = commentRepository.findById(cNo);
        Comment comment = null;
        if(oc.isPresent()){
            comment = oc.get();
        }
        Long pNo = comment.getPost().getPNo();
        commentRepository.delete(comment);
        commentRepository.flush();
        return pNo;
    }
}