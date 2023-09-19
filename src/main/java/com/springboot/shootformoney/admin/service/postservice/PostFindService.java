package com.springboot.shootformoney.admin.service.postservice;

import com.springboot.shootformoney.post.Post;
import com.springboot.shootformoney.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostFindService {

    private PostRepository postRepository;

    @Autowired
    public PostFindService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 전체 post 조회
    public List<Post> findAllposts() {
        return postRepository.findAll();
    }

    // post pNo 조회
    public Post findPostPNo (Long pNo) {
        Post post = postRepository.findOne(pNo);
        if(post != null) {
            return post;
        } else {
            throw new IllegalArgumentException("존재하지 않는 게시물입니다.");
        }
    }

}
