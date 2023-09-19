package com.springboot.shootformoney.post;


import com.springboot.shootformoney.board.entity.Board;
import com.springboot.shootformoney.board.repository.BoardRepository;
import com.springboot.shootformoney.file.File;
import com.springboot.shootformoney.file.FileRepository;
import com.springboot.shootformoney.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final FileRepository fileRepository;
    //저장
    @Transactional
    public Long savePost(PostDTO postDto){
        Board board = boardRepository.findBybNo(postDto.getBNo());
        if (board == null) {
            throw new IllegalArgumentException("해당 번호의 게시판이 존재하지 않습니다: " + postDto.getBNo());
        }

        Post post = new Post();
        post.setPTitle(postDto.getPTitle());
        post.setPContent(postDto.getPContent());

        // 연관 관계 설정
        post.setBoard(board);


        List<File> files = new ArrayList<>();
        for (String fileName : postDto.getFiles()) {
            File file = new File();
            file.setFileName(fileName);
            file.setOriginalFileName(fileName);  // 원본 파일명 설정. 실제 애플리케이션에서는 적절한 값을 설정해야 합니다.
            files.add(file);
        }

        post.setFiles(files);

        // Save the post
        postRepository.save(post,postDto.getBNo(), files);

        return post.getPNo();

    }

    //삭제
    @Transactional
    public void deletePost(Long pNo) {
        Post post = postRepository.findOne(pNo);
        if (post != null) {
            postRepository.delete(post);
        }
        else {
            throw new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않습니다");
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

    //단일 조회
    @Transactional
    public PostDTO findPost(Long pNo) {
        Post 	post = 	postRepository.findOne(pNo);
        if (post != null) {
            post.incrementViewCount();
            return 	PostDTO.of(post);
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




}
