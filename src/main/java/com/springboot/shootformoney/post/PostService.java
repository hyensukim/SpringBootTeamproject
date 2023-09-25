package com.springboot.shootformoney.post;


import com.querydsl.core.BooleanBuilder;
import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.utils.MemberUtil;
import com.springboot.shootformoney.post.repository.PostRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.data.domain.Sort.by;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final MemberUtil memberUtil;
    private final PostRepositoryInterface postRepositoryInterface;
    private final PagingRepository pagingRepository;

    //게시글 저장(확인)
    @Transactional
    public Long savePost(PostDTO postDto){
        Board board = boardRepository.findBybNo(postDto.getBNo());
        String title = postDto.getPTitle();
        String content = postDto.getPContent();
        Member member = null;

        // 유효성 검사
        if(memberUtil.isLogin()){
            if (board == null) {
                throw new IllegalArgumentException("게시판을 선택해주세요 : " + postDto.getBNo());
            }
            if(title == null || title.isBlank()){
                throw new IllegalArgumentException("제목을 입력해주세요."+ postDto.getBNo());
            }

            if(content == null || content.isBlank()){
                throw new IllegalArgumentException("내용을 입력해주세요."+ postDto.getBNo());
            }
            member = memberUtil.getEntity();
        }else{
            throw new IllegalArgumentException("로그인 후 글작성 해주세요."+ postDto.getBNo());
        }

        Post post = new Post();
        post.setPTitle(title);
        post.setPContent(content.replace("<p>","").replace("</p>",""));
        post.setMember(member);
        post.setBoard(board);

        // Save the post
        postRepository.save(post);
        return post.getPNo();
    }

    //게시글 삭제(확인)
    @Transactional
    public void deletePost(Long pNo) {
        Post post = postRepository.findOne(pNo);
        if (post != null) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않습니다.");
        }
    }

    //게시글 수정(확인)
    @Transactional
    public void updatePost(Long pNo, PostDTO updatedPost) {
        Post post = postRepository.findOne(pNo);
        String pTitle = updatedPost.getPTitle();
        String pContent = updatedPost.getPContent();
        if(post == null){
            throw new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않습니다.");
        }
        if(pTitle == null || pTitle.isBlank()){
            throw new IllegalArgumentException("제목을 입력 해주세요.");
        }
        if(pContent == null || pContent.isBlank()){
            throw new IllegalArgumentException("내용을 입력 해주세요.");
        }
        pContent = pContent.replace("<p>","")
                .replace("</p>","");
        post.update(pTitle, pContent);
    }

    // 게시판 목록 조회()
    public Page<Post> findByBoardWithPage(PostSearchInfo postSearchInfo, Long bNo){
        QPost post = QPost.post;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(post.board.bNo.eq(bNo));

        int page = postSearchInfo.getPage();
//            int pageSize = postSearchInfo.getPageSize();

        // 게시판 존재 여부 확인
        if(!boardRepository.existsBybNo(bNo)){
            throw new IllegalArgumentException("Invaild bNo: " + bNo);
        }
        // 게시판 정보 불러오기
        Board board = boardRepository.findBybNo(bNo);
    //        int page = board.getBUnitNo();  // 페이지 개수
        int pageSize = board.getBPageNo(); // 리스트 개수

        page = Math.max(page, 1);
        pageSize = pageSize < 1 ? 15 : pageSize;

        Pageable pageable = PageRequest.of(page-1,pageSize,Sort.by(desc("createdAt")));
        Page<Post> boardPostList = pagingRepository.findAll(andBuilder,pageable);
        return boardPostList;
    }


    // 게시판 전체 목록 조회(확인)
    public Page<Post> gets(PostSearchInfo postSearchInfo) {
        QPost post = QPost.post;
        BooleanBuilder andBuilder = new BooleanBuilder();
        int page = 0, pageSize = 0;
        String sOpt="", sKey="";
        if(postSearchInfo != null) {
            page = postSearchInfo.getPage();
            pageSize = postSearchInfo.getPageSize();
            sOpt = postSearchInfo.getSOpt();
            sKey = postSearchInfo.getSKey();
        }

        page = Math.max(page, 1);
        pageSize = pageSize < 1 ? 15 : pageSize;

        /** 검색 조건 처리 S */
        if (sOpt != null && !sOpt.isBlank() && sKey != null && !sKey.isBlank()) {
            sOpt = sOpt.trim();
            sKey = sKey.trim();

            if (sOpt.equals("pTitle")) { // 게시판 제목
                andBuilder.and(post.pTitle.contains(sKey));
            } else if (sOpt.equals("pContent")) { // 게시판 내용
                andBuilder.and(post.pContent.contains(sKey));
            }
        }
        /** 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(desc("createdAt")));
        Page<Post> data = postRepositoryInterface.findAll(andBuilder, pageable);
        return data;
    }

    //단일 조회
    @Transactional
    public Post findPostWithView(Long pNo) {
        Post post = postRepository.findOne(pNo);
        if (post != null) {
            post.incrementViewCount();
            return post;
        } else {
            throw new IllegalArgumentException("**해당 아이디의 게시물이 존재하지 않습니다.");
        }
    }

    @Transactional
    public Post findPost(Long pNo) {
        Post post = postRepository.findOne(pNo);
        if (post != null) {
            return post;
        } else {
            throw new IllegalArgumentException("**해당 아이디의 게시물이 존재하지 않습니다.");
        }
    }
}
