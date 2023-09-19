package com.springboot.shootformoney.admin.service.postservice;

import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.enum_.Grade;
import com.springboot.shootformoney.member.enum_.Role;
import com.springboot.shootformoney.post.Post;
import com.springboot.shootformoney.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    // bName 조회
    public List<Post> findPostBybName(String bName) { return postRepository.findByBoard_BName(bName); }

    public List<Post> searchPosts(String category, String query) {
        switch (category) {
            case "pNo":
                Post post = postRepository.findOne(Long.parseLong(query));
                return post != null ? Arrays.asList(post) : new ArrayList<>();

            case "mId":
                return postRepository.findByMemberId(query);

            case "mNickName":
                return postRepository.findByMember_MNickName(query);

            case "bName":
                return postRepository.findByBoard_BName(query);

            case "pTitle":
                return postRepository.findByTitle(query);

            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
    }

}
