package com.springboot.shootformoney.admin.controller.postcontroller;

import com.springboot.shootformoney.admin.service.postservice.PostAdminService;
import com.springboot.shootformoney.post.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@Controller
@RequestMapping("/admin/post")
public class PostManagementController {

    @Autowired
    private PostAdminService postAdminService;

    // 모든 게시물 조회
    @GetMapping("/")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return new ResponseEntity<>(postAdminService.findAllPosts(), HttpStatus.OK);
    }

    // 게시판 이름별 모든 게시물 조회
    @GetMapping("/boards/{bName}")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable String bName) {
        return new ResponseEntity<>(postAdminService.findPostsByBoardName(bName), HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{pNo}")
    public ResponseEntity<Void> deletePost(@PathVariable Long pNo) {
        postAdminService.deletePost(pNo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
