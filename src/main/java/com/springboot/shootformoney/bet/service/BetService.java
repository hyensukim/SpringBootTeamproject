package com.springboot.shootformoney.bet.service;

import com.springboot.shootformoney.bet.entity.Bet;
import com.springboot.shootformoney.bet.repository.BetRepository;
import com.springboot.shootformoney.bet.repository.EuroPoolRepository;
import com.springboot.shootformoney.game.dto.data.Result;
import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.game.repository.GameRepository;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/*
* 배팅 관련 서비스 구현 클래스
* Author: Hyedokal(https://www.github.com/Hyedokal)
*/
@Service
public class BetService {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private EuroPoolRepository euroPoolRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MemberRepository memberRepository;

    //고객이 배팅하면, Bet 테이블에 기록을 저장하는 메서드
    @Transactional
    public String bet(Long gNo, String expect, Integer euro) {
        Optional<Game> optionalGame = gameRepository.findById(gNo); //gNo로 경기 데이터를 찾아 온다.
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();  // 현재 로그인한 사용자의 정보를 가져옴
//        Long mNo = memberInfo.getMNo();  // mNo 값을 읽어옴
        if (optionalGame.isPresent()) {
            Bet bet = new Bet();
            bet.setGame(optionalGame.get());
            bet.setExpect(Result.valueOf(expect));
            bet.setBtMoney(euro);

//            Optional<Member> optionalMember = memberRepository.findById(mNo); //mNo로 회원 데이터를 찾아 온다.

//            if(optionalMember.isPresent()){
//                bet.setMember(optionalMember.get());  // 회원 데이터 설정
//            }
            Bet savedBet = betRepository.save(bet);  // 저장하고 결과를 받아옴

            if (savedBet.getBtNo() != null) {  // 성공적으로 저장되었는지 확인
                return "Success";
            } else {
                throw new RuntimeException("Failed to save the bet");  // 실패했으면 예외 발생
            }
        }
        return "Failure";
    }

    //올인 배팅 메서드 프론트에서 '올인'버튼을 클릭하면 자동으로 input 칸에 최고 배팅금이 들어오도록 하기.(프론트가 담당)
    @Transactional
    public String betAllIn(Long gNo, String expect){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();  // 현재 로그인한 사용자의 정보를 가져옴
//        Long mNo = memberInfo.getMNo();  // mNo 값을 읽어옴

        Optional<Game> optionalGame = gameRepository.findById(gNo); //gNo로 경기 데이터를 찾아 온다.

        if(optionalGame.isPresent()){
            Bet bet = new Bet();
            bet.setGame(optionalGame.get());
            bet.setExpect(Result.valueOf(expect));

//            Optional<Member> optionalMember = memberRepository.findById(mNo); //mNo로 회원 데이터를 찾아 온다.

//            if(optionalMember.isPresent()){
//                Member member = optionalMember.get();
//                Integer allEuro = (member.getEuro() / 10000)  // 회원의 보유 euro 전체, 만 단위
//
//                bet.setBtMoney(allInEuroAmount);  // 회원의 보유 euro 전체를 배팅 금액으로 설정
//                bet.setMember(member);  // 회원 데이터 설정

                Bet savedBet = betRepository.save(bet);  // 저장하고 결과를 받아옴

//                if (savedBet != null && savedBet.getBtId() != null) {  // 성공적으로 저장되었는지 확인
//                    return "Success";
//                } else {
//                    throw new RuntimeException("Failed to save the bet");  // 실패했으면 예외 발생
//                }
//            }

        }

//        throw new IllegalArgumentException("Invalid gNo or mNo: " + gNo + ", " + mNo);
        return "Failure";
    }


    //배팅 취소 메서드
    @Transactional
    public String betCancel(Long btNo){
        Optional<Bet> optionalBet = betRepository.findByBtNo(btNo); //btId로 배팅 데이터를 찾아 온다.

        if(optionalBet.isPresent()){
            Bet bet = optionalBet.get();
            betRepository.delete(bet);  // 찾아온 배팅 데이터 삭제
            return "Success";
        }

        throw new IllegalArgumentException("Invalid btNo: " + btNo);
    }

}
