package com.springboot.shootformoney.member.services;

import com.springboot.shootformoney.bet.entity.Bet;
import com.springboot.shootformoney.bet.repository.BetRepository;
import com.springboot.shootformoney.member.dto.PageInfo;
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
public class MemberListService {

    private final PostRepository postRepository;
    private final Post2Repository post2Repository;
    private final BetRepository betRepository;

    public Page<Post> getsPostWithPages(PageInfo pageInfo, Long mNo){

        int page = pageInfo.getPage();
        int pageSize = pageInfo.getPageSize();
        List<Post> posts = postRepository.findByMemberNo(mNo);

        page = Math.max(page, 1);
        pageSize = pageSize < 1 ? 15 : pageSize;

        if(posts == null){ throw new NullPointerException("회원이 작성한 게시글이 없습니다.");}

        Pageable pageable = PageRequest.of(page-1,pageSize,Sort.by(Sort.Order.desc("createdAt")));
        Page<Post> myPostList = post2Repository.findAll(pageable);

        return myPostList;
    }

    public Page<Bet> getsBetWithPages(PageInfo pageInfo, Long mNo){

        int page = pageInfo.getPage();
        int pageSize = pageInfo.getPageSize();
        List<Bet> bets = betRepository.findBymNo(mNo);

        page = Math.max(page, 1);
        pageSize = pageSize < 1 ? 15 : pageSize;

        if(bets == null){ throw new NullPointerException("회원이 배팅한 내역이 없습니다.");}

        Pageable pageable = PageRequest.of(page-1,pageSize,Sort.by(Sort.Order.desc("btTime")));
        Page<Bet> myBetList = betRepository.findAll(pageable);
        return myBetList;
    }

}
