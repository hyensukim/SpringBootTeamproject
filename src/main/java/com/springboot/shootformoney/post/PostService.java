package com.springboot.shootformoney.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;


    //저장
    @Transactional
    public Long savePost(Post post){
        postRepository.save(post);
        return post.getPNo();
    }

    //삭제
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findOne(id);
        if (post != null) {
            postRepository.delete(post);
        }
        else {
            throw new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않습니다");
        }
    }

    //수정
    @Transactional
    public void updatePost(Long id, String title, String content) {
        Post post = postRepository.findOne(id);
        if (post != null) {
            post.update(title, content);
        } else {
            throw new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않습니다.");
        }
    }


    //전체 조회
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    //단일 조회
    public Post findPost(Long id) {
        Post post = postRepository.findOne(id);
        if (post != null) {
            post.incrementViewCount();
            return post;
        } else {
            throw new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않습니다.");
        }
    }

    // 제목으로 찾기
    public List<Post> findPostsByTitle(String title) {
        return postRepository.findByTitle(title);
    }

}
