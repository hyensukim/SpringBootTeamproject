package com.springboot.shootformoney.bet.service;

import com.springboot.shootformoney.bet.entity.Bet;
import com.springboot.shootformoney.bet.entity.EuroPool;
import com.springboot.shootformoney.bet.repository.BetRepository;
import com.springboot.shootformoney.bet.repository.EuroPoolRepository;
import com.springboot.shootformoney.game.dto.data.Result;
import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.game.repository.GameRepository;
import com.springboot.shootformoney.member.entity.Euro;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.EuroRepository;
import com.springboot.shootformoney.member.repository.MemberRepository;
import com.springboot.shootformoney.member.utils.MemberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipalNotFoundException;
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
    private MemberUtil memberUtil;

    @Autowired
    private EuroRepository euroRepository;


    //고객이 배팅하면, Bet 테이블에 기록을 저장하는 메서드
    @Transactional
    public Bet bet(Long gNo, String expect, Integer betAmount) {
        Game game = gameRepository.findById(gNo)
                .orElseThrow(() -> new RuntimeException("해당 경기 정보가 없습니다.")); //gNo로 경기 데이터를 찾아 온다.
        Bet bet = new Bet();
        bet.setGame(game);
        bet.setExpect(Result.valueOf(expect));
        bet.setBtMoney(betAmount);
        bet.setBtTime(LocalDateTime.now());
        bet.setMember(memberUtil.getEntity());
        //배팅 정보 저장 후 가져옴.
        return betRepository.save(bet);
    }

    //취소할 배팅을 불러오는 메서드
    @Transactional
    public Bet findToCancel(Long btNo){
        Optional<Bet> bet = betRepository.findByBtNo(btNo);
        if(bet.isPresent()){
           return bet.get();
        }
        return null;
    }
    //배팅 취소 메서드
    @Transactional
    public Bet betCancel(Long btNo){
        Bet bet = betRepository.findByBtNo(btNo) //btId로 배팅 데이터를 찾아 온다.
                                .orElseThrow(() -> new RuntimeException("배팅 정보가 잘못되었습니다."));
        betRepository.delete(bet);  // 찾아온 배팅 데이터 삭제
        return bet;

    }

    //경기결과가 나오면 배당률을 계산해서 모든 배팅률이 업데이트되지 않은 배팅내역에 배당률을 집어넣는다.
    @Transactional
    public void calcBtRatio(){
        try{
            List<Game> finishedMatches = gameRepository.findAllFinishedMatches();
            for(Game game : finishedMatches) {
                EuroPool targetEuroPool = euroPoolRepository.findByGame_gNo(game.getGNo());
                //걸린 유로의 총합.
                double euroSum = (double) targetEuroPool.getWinEuro() + targetEuroPool.getDrawEuro()
                        + targetEuroPool.getLoseEuro();
                //각 결과별 배당률.
                double winBtRatio = Math.round((euroSum / targetEuroPool.getWinEuro()) * 100.0) / 100.0;
                double drawBtRatio = Math.round((euroSum / targetEuroPool.getDrawEuro()) * 100.0) / 100.0;
                double loseBtRatio = Math.round((euroSum / targetEuroPool.getLoseEuro()) * 100.0) / 100.0;

                List<Bet> bets = betRepository.findAllByGame_gNo(game.getGNo());
                for (Bet bet : bets) {
                    if (bet.getExpect() == Result.WIN) {
                        bet.setBtRatio(winBtRatio);
                    } else if (bet.getExpect() == Result.DRAW) {
                        bet.setBtRatio(drawBtRatio);
                    } else if(bet.getExpect() == Result.LOSE) {
                        bet.setBtRatio(loseBtRatio);
                    }
                }
            }
        } catch (RuntimeException e){
            System.err.println("calcBtRatio()에서 오류 발생");
        }
    }

    //경기결과가 나오면 지급금을 지급하는 메서드.
    @Transactional
    public void dividend(){
        try {
            //끝난 경기들 가져오기.
            List<Game> finishedGames = gameRepository.findAllFinishedMatches();

            for (Game game : finishedGames) {
                List<Bet> wonBets = betRepository.findByResultAndExpect(game);
                for (Bet bet : wonBets) {
                    Member member = bet.getMember();
                    double fee = member.getGrade().getFee();

                    //배당금 = 배팅금 * (배당률 - 1) (만 단위, 배팅금 보전)
                    double prizeValue = bet.getBtMoney() * (bet.getBtRatio() - 1);
                    //지급금 = 배당금 * (1 - 수수료) + 배팅금
                    Integer addValue = (int) (prizeValue * (1 - fee) + bet.getBtMoney()) * 10000;

                    //현재 사용자의 유로 보유량 조회.
                    Integer originalValue = euroRepository.findBymNo(member.getMNo()).getValue();
                    //Euro 보유량 업데이트.
                    Euro euroAccount = euroRepository.findBymNo(member.getMNo());
                    euroAccount.setValue(originalValue + addValue);

                    //Bet엔터티의 중복검사부분 업데이트
                    bet.setEndPaid((byte) 1);
                }
            }
        } catch (Exception e){
            System.out.println("오류가 발생했습니다.");
        }
    }
}
