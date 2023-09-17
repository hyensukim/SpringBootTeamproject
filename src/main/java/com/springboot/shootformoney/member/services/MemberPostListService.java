package com.springboot.shootformoney.member.services;

import com.springboot.shootformoney.PageHandler;
import com.springboot.shootformoney.member.dto.BoardSearch;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.exceptions.MemberNotExistException;
import com.springboot.shootformoney.member.repository.MemberRepository;
import com.springboot.shootformoney.member.repository.Post2Repository;
import com.springboot.shootformoney.post.Post;
import com.springboot.shootformoney.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberPostListService {

    private final PostRepository postRepository;
    private final Post2Repository post2Repository;

    public Page<Post> getsWithPages(BoardSearch boardSearch, Long mNo){

        int page = boardSearch.getPage();
        int pageSize = boardSearch.getPageSize();
        List<Post> posts = postRepository.findByMemberNo(mNo);

        page = Math.max(page, 1);
        pageSize = pageSize < 1 ? 20 : pageSize;

        Pageable pageable = PageRequest.of(page-1,pageSize,Sort.by(Sort.Order.desc("createdAt")));
        Page<Post> myPostList = post2Repository.findAll(pageable);

        return myPostList;
    }
}
