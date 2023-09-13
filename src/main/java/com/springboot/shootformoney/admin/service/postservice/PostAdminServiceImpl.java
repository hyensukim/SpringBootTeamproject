package com.springboot.shootformoney.admin.service.postservice;

import com.springboot.shootformoney.post.Post;
import com.springboot.shootformoney.post.PostDTO;
import com.springboot.shootformoney.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostAdminServiceImpl implements PostAdminService {

    @Autowired
    private PostRepository postRepository;

    // 게시글 전체 조회
    @Override
    public List<PostDTO> findAllPosts() {
        return postRepository.findAllWithBoard().stream()
                .map(PostDTO::of)
                .collect(Collectors.toList());
    }

    // 게시판별 게시글 조회
    @Override
    public List<PostDTO> findPostsByBoardName(String bName) {
        return postRepository.findByBoard_BName(bName).stream()
                .map(PostDTO::of)
                .collect(Collectors.toList());
    }

    // 게시글 삭제
    @Override
    @Transactional
    public void deletePost(Long pNo) {
        Post post = postRepository.findOne(pNo);
        if (post != null) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("해당 번호의 게시글이 존재하지 않습니다: " + pNo);
        }
    }

}
