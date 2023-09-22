package com.springboot.shootformoney.member.services;

import com.querydsl.core.BooleanBuilder;
import com.springboot.shootformoney.bet.entity.Bet;
import com.springboot.shootformoney.bet.entity.QBet;
import com.springboot.shootformoney.bet.repository.BetRepository;
import com.springboot.shootformoney.member.dto.SearchInfo;
import com.springboot.shootformoney.member.utils.MemberUtil;
import com.springboot.shootformoney.post.PagingRepository;
import com.springboot.shootformoney.post.Post;
import com.springboot.shootformoney.post.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberListService {

    private final BetRepository betRepository;
    private final PagingRepository pagingRepository;
    private final MemberUtil memberUtil;

    public Page<Post> getsPostWithPages(SearchInfo searchInfo, Long mNo){
        QPost post = QPost.post;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(post.member.mNo.eq(mNo));

        int page = searchInfo.getPage();
        int pageSize = searchInfo.getPageSize();
        page = Math.max(page, 1);
        pageSize = pageSize < 1 ? 15 : pageSize;

        //검색 기능 추가 시 구현
        String sOpt = searchInfo.getSOpt();
        String sKey = searchInfo.getSKey();
        if (sOpt != null && !sOpt.isBlank() && sKey != null && !sKey.isBlank()) {
            sOpt = sOpt.trim();
            sKey = sKey.trim();

//            if (sOpt.equals("all")) { // 통합 검색 - bId, bName
//                BooleanBuilder orBuilder = new BooleanBuilder();
//                orBuilder.or(post.bId.contains(sKey))
//                        .or(board.bName.contains(sKey));
//                andBuilder.and(orBuilder);
//
//            } else if (sopt.equals("bId")) { // 게시판 아이디 bId
//                andBuilder.and(board.bId.contains(skey));
//            } else if (sopt.equals("bName")) { // 게시판명 bName
//                andBuilder.and(board.bName.contains(skey));
//            }
        }

        Pageable pageable = PageRequest.of(page-1,pageSize,Sort.by(Sort.Order.desc("createdAt")));
        Page<Post> myPostList = pagingRepository.findAll(andBuilder,pageable);

        return myPostList;
    }

    public List<Bet> getsBetList(Long mNo){
        List<Bet> betList = new ArrayList<>();
        QBet bet = QBet.bet;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(bet.member.mNo.eq(mNo));
        Sort sort = Sort.by(Sort.Order.asc("endPaid"),Sort.Order.desc("btTime"));
        Iterable<Bet> it = betRepository.findAll(andBuilder,sort);
        if(it == null){
            throw new NullPointerException("회원이 베팅한 항목이 없습니다.");
        }
        it.forEach(betList::add);
        return betList;
    }

}
