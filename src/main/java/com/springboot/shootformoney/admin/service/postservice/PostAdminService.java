package com.springboot.shootformoney.admin.service.postservice;

import com.springboot.shootformoney.post.PostDTO;

import java.util.List;

public interface PostAdminService {

    List<PostDTO> findAllPosts();

    void deletePost(Long pNo);

    List<PostDTO> findPostsByBoardName(String bName);

    // 회원 닉네임으로 게시글 조회
    List<PostDTO> findPostsByMemberNickname(String mNickname);



}
