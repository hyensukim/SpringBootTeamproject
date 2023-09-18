package com.springboot.shootformoney.member.services;

import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankListService {

    private final MemberRepository memberRepository;

    // 상위 10개의 데이터를 조회(기준 : 1st level, 2nd stack(누적 배팅액))
    public List<Member> getRankList(){
        List<Member> rank10 = new ArrayList<>();
        List<Member> rankList = memberRepository.findByMemberOrderBymLevelAAndMStack();
        for(int i=0; i<10; i++){
            rank10.add(rankList.get(i));
        }
        return rank10;
    }

    // 페이지 처리한 전체 랭킹 데이터를 10개씩 조회
    public Page<Member> getRankListWithPage(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC,"mLevel","mStack"));
        Page<Member> members = memberRepository.findAll(pageable);
        return members;
    }
}
