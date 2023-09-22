package com.springboot.shootformoney.post;


import com.springboot.shootformoney.admin.dto.AdminSearchInfo;
import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.member.dto.MemberInfo;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import com.springboot.shootformoney.member.utils.MemberUtil;
import com.springboot.shootformoney.post.repository.PostRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

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
        post.setPTitle(postDto.getPTitle());
        post.setPContent(postDto.getPContent());
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
    public void updatePost(Long pNo, String pTitle, String pContent) {
        Post post = postRepository.findOne(pNo);
        if (post != null) {
            post.update(pTitle, pContent);
        } else {
            throw new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않습니다.");
        }
    }


    //전체 조회
    public List<Post> findAllPosts() {
        return 	postRepository.findAll();
    }

//    단일 조회
@Transactional
public Post findPost(Long pNo) {
    Post post = postRepository.findOne(pNo);
    if (post != null) {
        post.incrementViewCount();
        return post;
    } else {
        throw new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않습니다.");
    }
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

        Pageable pageable = PageRequest.of(page - 1, pageSize, by(Sort.Order.desc("createdAt")));
        Page<Post> postList = postRepositoryInterface.findAll(pageable);
        return postList;
    }

}
