package com.springboot.shootformoney.bet.service;

/*
* Member의 보유 Euro량을 배팅(취소)함에 따라 가감하는 로직 구현.
* Author: Hyedokal(https://www.github.com/Hyedokal)
*/

import com.springboot.shootformoney.bet.entity.Bet;
import com.springboot.shootformoney.bet.repository.BetRepository;
import com.springboot.shootformoney.member.entity.Euro;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.EuroRepository;
import com.springboot.shootformoney.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EuroService {

    @Autowired
    private EuroRepository euroRepository;

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private MemberRepository memberRepository;

    //유저가 배팅하면 유로 보유량을 배팅금만큼 감산하는 메서드.
    @Transactional
    public void decreaseEuro(Long mNo, Integer betValue) {
        Euro currentEuro = euroRepository.findBymNo(mNo);  // 현재 보유 Euro 조회
        Integer minusValue = betValue * 10000; //betValue는 프론트 계획 상 10000 단위를 뗀 상태로 들어옴.
        if (currentEuro.getValue() < minusValue) {
            throw new IllegalArgumentException("유로가 부족합니다.");  // 보유금액보다 배팅금액이 크면 예외 발생
        }

        Integer updatedValue = currentEuro.getValue() - minusValue;  // 배팅 금액만큼 감산

        currentEuro.setValue(updatedValue);  // 갱신된 금액으로 설정

        euroRepository.save(currentEuro);  // 변경된 정보 저장
    }

    //유저가 배팅을 취소하면 유로 보유량을 취소한 배팅금만큼 가산하는 메서드.
    @Transactional
    public void rollbackEuro(Long mNo, Long betId) {
        // 해당 Member와 연결된 Bet 데이터를 찾아 온다.
        Optional<Bet> optionalBet = betRepository.findById(betId);
        Bet bet = optionalBet.get();
        Integer rollbackValue = bet.getBtMoney() * 10000;  // 취소할 배팅금액 조회 후 1만을 곱합.

        // 해당 Member에 연결된 Euro 데이터를 찾아 온다.
        Euro currentEuro = euroRepository.findBymNo(mNo);
        Integer updatedValue = currentEuro.getValue() + rollbackValue;  // 취소한 배팅금만큼 가산

        currentEuro.setValue(updatedValue);  // 갱신된 금액으로 설정

        euroRepository.save(currentEuro);  // 변경된 정보 저장
    }
}
