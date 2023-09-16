package com.springboot.shootformoney.bet.service;

import com.springboot.shootformoney.bet.entity.Bet;
import com.springboot.shootformoney.bet.repository.BetRepository;
import com.springboot.shootformoney.bet.repository.EuroPoolRepository;
import com.springboot.shootformoney.game.dto.data.Result;
import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.game.repository.GameRepository;
import com.springboot.shootformoney.member.entity.Euro;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.EuroRepository;
import com.springboot.shootformoney.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/*
* 배팅 관련 서비스(배팅 실행, 배팅 취소, 정산 후 지급금 지급) 구현 클래스
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

    @Autowired
    private EuroRepository euroRepository;


    //고객이 배팅하면, Bet 테이블에 기록을 저장하는 메서드
    @Transactional
    public Bet bet(Long gNo, String expect, Integer euro) {
        Game game = gameRepository.findById(gNo)
                .orElseThrow(() -> new RuntimeException("해당 경기 정보가 없습니다.")); //gNo로 경기 데이터를 찾아 온다.
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();  // 현재 로그인한 사용자의 정보를 가져옴
//        Long mNo = memberInfo.getMNo();  // mNo 값을 읽어옴
//        Member member = memberRepository.findById(mNo)
//                .orElseThrow(() -> new RuntimeException("로그인 정보가 만료되었습니다. 다시 로그인하세요."));

        Bet bet = new Bet();
        bet.setGame(game);
        bet.setExpect(Result.valueOf(expect));
        bet.setBtMoney(euro);
        bet.setBtTime(LocalDateTime.now());
//        bet.setMember(memberRepository.findById(mNo));

        //배팅 정보 저장 후 가져옴.
        return betRepository.save(bet);
    }

    //올인 배팅 메서드 프론트에서 '올인'버튼을 클릭하면 자동으로 input 칸에 최고 배팅금이 들어오도록 하기.
    //프론트에서 버튼누를 때 구현해야 할 것 같음. 나중에 필요없으면 메서드 지울예정. 예성이 화이팅 ^^!!! <3 <3
//    @Transactional
//    public String betAllIn(Long gNo, String expect){
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();  // 현재 로그인한 사용자의 정보를 가져옴
////        Long mNo = memberInfo.getMNo();  // mNo 값을 읽어옴
//
//        Optional<Game> optionalGame = gameRepository.findById(gNo); //gNo로 경기 데이터를 찾아 온다.
//
//        if(optionalGame.isPresent()){
//            Bet bet = new Bet();
//            bet.setGame(optionalGame.get());
//            bet.setExpect(Result.valueOf(expect));
//
////            Optional<Member> optionalMember = memberRepository.findById(mNo); //mNo로 회원 데이터를 찾아 온다.
//
////            if(optionalMember.isPresent()){
////                Member member = optionalMember.get();
////                Integer allEuro = (member.getEuro() / 10000)  // 회원의 보유 euro 전체, 만 단위
////
////                bet.setBtMoney(allInEuroAmount);  // 회원의 보유 euro 전체를 배팅 금액으로 설정
////                bet.setMember(member);  // 회원 데이터 설정
//
//                Bet savedBet = betRepository.save(bet);  // 저장하고 결과를 받아옴
//
////                if (savedBet != null && savedBet.getBtId() != null) {  // 성공적으로 저장되었는지 확인
////                    return "Success";
////                } else {
////                    throw new RuntimeException("Failed to save the bet");  // 실패했으면 예외 발생
////                }
////            }
//
//        }

//        throw new IllegalArgumentException("Invalid gNo or mNo: " + gNo + ", " + mNo);
//        return "Failure";
//    }


    //배팅 취소 메서드
    @Transactional
    public Bet betCancel(Long btNo){
        Bet bet = betRepository.findByBtNo(btNo) //btId로 배팅 데이터를 찾아 온다.
                                .orElseThrow(() -> new RuntimeException("배팅 정보가 잘못되었습니다."));
        betRepository.delete(bet);  // 찾아온 배팅 데이터 삭제
        return bet;

    }

    //배팅내역 전체 조회 메서드
//    public List<Bet> getMyAllBets(){
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Long mNo = ((MemberInfo) principal).getMNo();
//        return betRepository.findBymNo(mNo);
//    }

    //경기결과가 나오면 지급금을 지급하는 메서드.
    @Transactional
    public String dividend(){
        try {
            //끝난 경기들 가져오기.
            List<Game> finishedGames = gameRepository.findAllFinishedMatches();

            for (Game game : finishedGames) {
                List<Bet> wonBets = betRepository.findByResultAndExpect();
                for (Bet bet : wonBets) {
                    Member member = bet.getMember();
                    //Double fee = member.getFee(); -> 수수료를 조회하는 부분을 만들자...

                    //현재 사용자의 유로 보유량 조회.
                    Integer originalValue = euroRepository.findBymNo(member.getMNo()).getValue();
                    //배당금 = 배팅금 * 배당률 - 배팅금 (만 단위)
                    double prizeValue = (bet.getBtMoney() * bet.getBtRatio()) - bet.getBtMoney();
                    //지급금 = 배당금 * (1 - 수수료) + 배팅금
                    Integer addValue = (int) (((prizeValue * (1 - (bet.getMember().getGrade().getFee()))
                                                + bet.getBtMoney()) * 10000));

                    //Euro 보유량 업데이트.
                    Euro euroAccount = euroRepository.findBymNo(member.getMNo());
                    euroAccount.setValue(originalValue + addValue);

                    //Bet엔터티의 중복검사부분 업데이트
                    bet.setEndPaid((byte) 1);
                }
            }
        } catch (Exception e){
            return "오류가 발생했습니다.";
        }
    return "정산 완료";
    }
}
