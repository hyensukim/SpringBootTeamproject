package com.springboot.shootformoney.admin.service.postservice;

import com.springboot.shootformoney.post.PostDTO;

import java.util.List;

public interface PostAdminService {

    List<PostDTO> findAllPosts();

    void deletePost(Long pNo);

    List<PostDTO> findPostsByBoardName(String bName);

}
