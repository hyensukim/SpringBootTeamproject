package com.springboot.shootformoney.admin.service.postservice;

import com.querydsl.core.BooleanBuilder;
import com.springboot.shootformoney.admin.dto.AdminSearchInfo;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.post.Post;
import com.springboot.shootformoney.post.PostRepository;
import com.springboot.shootformoney.post.repository.PostRepositoryInterface;
import com.springboot.shootformoney.post.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.domain.Sort.by;

@Service
@RequiredArgsConstructor
public class PostFindService {

    private final PostRepository postRepository;
    private final PostRepositoryInterface postRepositoryInterface;
    private final BoardRepository boardRepository;


    // 전체 post 조회
    public List<Post> findAllposts() {
        return postRepository.findAll();
    }

    // post pNo 조회
    public Post findPostPNo(Long pNo) {
        Post post = postRepository.findOne(pNo);
        if (post != null) {
            return post;
        } else {
            throw new IllegalArgumentException("존재하지 않는 게시물입니다.");
        }
    }

    // 전체 리스트 조회 서비스 (+ 페이징 처리)
    public Page<Post> getsAdminPostWithPages(AdminSearchInfo adminSearchInfo) {


        int page = adminSearchInfo.getPage();
        int pageSize = adminSearchInfo.getPageSize();
        page = Math.max(page, 1);
        pageSize = pageSize < 1 ? 15 : pageSize;

        Pageable pageable = PageRequest.of(page - 1, pageSize, by(Sort.Order.desc("createdAt")));
        Page<Post> adminPostList = postRepositoryInterface.findAll(pageable);

        return adminPostList;
    }

    // 검색(필터링) 페이징 처리
    public Page<Post> searchPosts(AdminSearchInfo adminSearchInfo) {
        int page = adminSearchInfo.getPage();
        int pageSize = adminSearchInfo.getPageSize();

        page = Math.max(page, 1);
        pageSize = pageSize < 1 ? 15 : pageSize;

        String sOpt = adminSearchInfo.getSOpt();
        String sKey = adminSearchInfo.getSKey();

        Pageable pageable = PageRequest.of(page - 1, pageSize,
                Sort.by(Sort.Order.desc("createdAt")));

        if (sOpt != null && sKey != null) {
            switch (sOpt) {
                case "pNo":
                    Long pNo = Long.parseLong(sKey);
                    return postRepositoryInterface.findBypNo(pNo,pageable);
                case "mId":
                    return postRepositoryInterface.findByMembermId(sKey, pageable);
                case "mNickName":
                    return postRepositoryInterface.findByMembermNickName(sKey, pageable);
                case "bName":
                    return postRepositoryInterface.findByBoardbName(sKey, pageable);
                case "pTitle":
                    return postRepositoryInterface.findBypTitle(sKey, pageable);
            }
        }

        return postRepositoryInterface.findAll(pageable);
    }
    
    
    // 게시글 일괄 삭제
    public void deleteMultiplePosts(List<Long> pNos) {
        if(pNos.isEmpty()) {
            throw new IllegalArgumentException("삭제할 게시글을 선택해주세요.");
        }

        List<Post> posts = postRepositoryInterface.findAllById(pNos);
        postRepositoryInterface.deleteInBatch(posts);
    }
}
