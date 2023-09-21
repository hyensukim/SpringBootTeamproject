package com.springboot.shootformoney.admin.service.postservice;

import com.querydsl.core.BooleanBuilder;
import com.springboot.shootformoney.admin.dto.postdto.PostSearchInfo;
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

    // bName 조회
    public List<Post> findPostBybName(String bName) {
        return postRepository.findByBoard_BName(bName);
    }

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


//        public Page<Post> getsAdminPostWithPages(PostSearchInfo postSearchInfo, Long bNo){
    public Page<Post> getsAdminPostWithPages(PostSearchInfo postSearchInfo) {
//        QPost post = QPost.post;
//        BooleanBuilder andBuilder = new BooleanBuilder();
//        andBuilder.and(post.board.bNo.eq(bNo));

        int page = postSearchInfo.getPage();
        int pageSize = postSearchInfo.getPageSize();
        page = Math.max(page, 1);
        pageSize = pageSize < 1 ? 15 : pageSize;

        Pageable pageable = PageRequest.of(page - 1, pageSize, by(Sort.Order.desc("createdAt")));
        Page<Post> adminPostList = postRepositoryInterface.findAll(pageable);

        return adminPostList;
    }


//    public Page<Post> getsAdminPostWithPages(PostSearchInfo postSearchInfo) {
//        int page = postSearchInfo.getPage();
//        int pageSize = postSearchInfo.getPageSize();
//        page = Math.max(page, 1);
//        pageSize = pageSize < 1 ? 15 : pageSize;
//
//        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("createdAt")));
//
//        // 검색 조건과 키워드를 가져오기
//        String category = postSearchInfo.getCategory();
//        String query = postSearchInfo.getQuery();
//
//        Page<Post> adminPostList = Page.empty();
//
//        if (category != null && !category.isEmpty() && query != null && !query.isEmpty()) {
//            // 검색 조건에 따른 동적 쿼리 생성
//            if ("pNo".equals(category)) {
//                adminPostList = postRepositoryInterface.findBypNo(Long.parseLong(query), pageable);
//            } else if ("mId".equals(category)) {
//                adminPostList = postRepositoryInterface.findByMembermId(query, pageable);
//            } else if ("mNickName".equals(category)) {
//                adminPostList = postRepositoryInterface.findByMembermNickName(query, pageable);
//            } else if ("bName".equals(category)) {
//                adminPostList = postRepositoryInterface.findByBoardbName(query, pageable);
//            } else if ("pTitle".equals(category)) {
//                adminPostList = postRepositoryInterface.findBypTitleContaining(query, pageable);
//            } else {
//                // 기타 검색 조건 처리
//                // adminPostList = postRepositoryInterface.findByCustomCondition(category, query, pageable);
//            }
//        } else {
//            // 검색 조건이 없을 경우 모든 게시물을 가져옴
//            adminPostList = postRepositoryInterface.findAll(pageable);
//        }
//
//        return adminPostList;
//    }
}
