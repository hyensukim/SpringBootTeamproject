package com.springboot.shootformoney.post;


import com.querydsl.core.BooleanBuilder;
import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.member.dto.SearchInfo;
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

    //저장
    @Transactional
    public Long savePost(PostDTO postDto){
        Board board = boardRepository.findBybNo(postDto.getBNo());
        Member member = null;
        String title = postDto.getPTitle();
        String content = postDto.getPContent();

        if(memberUtil.isLogin()){
            if (board == null) {
                throw new IllegalArgumentException("해당 번호의 게시판이 존재하지 않습니다. 게시판을 선택해주세요 : " + postDto.getBNo());
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

    //삭제
    @Transactional
    public void deletePost(Long pNo) {
        Post post = postRepository.findOne(pNo);
        if (post != null) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않습니다.");
        }
    }

    //수정
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

    public Page<Post> findByBoardWithPage(PostSearchInfo postSearchInfo, Long bNo){
        QPost post = QPost.post;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(post.board.bNo.eq(bNo));

        int page = postSearchInfo.getPage();
        int pageSize = postSearchInfo.getPageSize();
        page = Math.max(page, 1);
        pageSize = pageSize < 1 ? 15 : pageSize;

        Pageable pageable = PageRequest.of(page-1,pageSize,Sort.by(desc("createdAt")));
        Page<Post> boardPostList = postRepositoryInterface.findAll(andBuilder,pageable);
        return boardPostList;
    }


    public Page<Post> gets(PostSearchInfo postSearchInfo) {
        QPost post = QPost.post;

        BooleanBuilder andBuilder = new BooleanBuilder();

        int page = postSearchInfo.getPage();
        int pageSize = postSearchInfo.getPageSize();
        page = page < 1 ? 1 : page;
        pageSize = pageSize < 1 ? 20 : pageSize;

        /** 검색 조건 처리 S */
        String sopt = postSearchInfo.getSOpt();
        String skey = postSearchInfo.getSKey();
        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            skey = skey.trim();
            sopt = sopt.trim();

            if (sopt.equals("all")) { // 통합 검색 - bId, bName
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(post.pTitle.contains(skey))
                        .or(post.pContent.contains(skey));
                andBuilder.and(orBuilder);

            } else if (sopt.equals("pTitle")) { // 게시판 제목
                andBuilder.and(post.pTitle.contains(skey));
            } else if (sopt.equals("pContent")) { // 게시판 내용
                andBuilder.and(post.pContent.contains(skey));
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

    //단일 조회(postResponseDto 사용 -> 순환 조회 방지)
    @Transactional
    public PostResponseDto findById(Long pNo){
        Post post = postRepositoryInterface.findById(pNo).orElseThrow(()->
                new IllegalArgumentException("게시글 조회 오류 : 게시글이 존재하지 않습니다."));
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }


        // 제목으로 찾기
    public List<Post> findPostsByTitle(String pTitle) {
        return postRepository.findByTitle(pTitle);
    }


    public List<Post> findPostsByMemberNickName(String mNickName) {
        return postRepository.findByMember_MNickName(mNickName);
    }


    public List<Post> findPostsByBoardBNo(Long bNo) {
        return postRepository.findByBoardBNo(bNo);
    }

    /**
     * 페이지 처리 목록
     */
    public Page<Post> getAllWithPage(PostSearchInfo postSearchInfo) {
        
        int page = postSearchInfo.getPage(); // 현재 페이지
        int pageSize = postSearchInfo.getPageSize(); // 페이지 크기
        page = Math.max(page, 1);
        pageSize = pageSize < 1 ? 10 : pageSize;

        Pageable pageable = PageRequest.of(page - 1, pageSize, by(desc("createdAt")));
        Page<Post> postList = postRepositoryInterface.findAll(pageable);
        return postList;
    }

//    public Page<Post> findByBoardBNoWithPage(Long bNo, PostSearchInfo postSearchInfo) {
//        int page = postSearchInfo.getPage(); // 현재 페이지
//        int pageSize = postSearchInfo.getPageSize(); // 페이지 크기
//        page = Math.max(page, 1);
//        pageSize = pageSize < 1 ? 10 : pageSize;
//
//        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC,"createdAt"));
//        return postRepositoryInterface.findByBoardBNo(bNo, pageable);
//    }

}
