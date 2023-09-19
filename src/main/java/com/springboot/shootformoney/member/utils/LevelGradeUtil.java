package com.springboot.shootformoney.member.utils;

import com.springboot.shootformoney.bet.entity.Bet;
import com.springboot.shootformoney.bet.repository.BetRepository;
import com.springboot.shootformoney.member.entity.Euro;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.enum_.Grade;
import com.springboot.shootformoney.member.exceptions.EuroNotExistException;
import com.springboot.shootformoney.member.exceptions.MemberNotExistException;
import com.springboot.shootformoney.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LevelGradeUtil {

    private final BetRepository betRepository;
    private final MemberRepository memberRepository;

    // 베팅 데이터 조회 및 집계(sum)
    public void setMemberStack(Member member){
        Long mNo = member.getMNo();
        List<Bet> bets = betRepository.findBymNo(mNo);
        Long sum = 0L;
        for(Bet b : bets){
            sum += b.getBtMoney();
        }
        member.setMStack(sum);
    }

    //누적 베팅 금액을 넣어 등급 조정.
    public Member gradeUp(Member member){
        setMemberStack(member);
        Long stack = member.getMStack();

        if(stack >= 10000 * 10000){
            member.setGrade(Grade.EUROPA);
        }

        if (stack >= 20000 * 10000) {
            member.setGrade(Grade.CHAMPIONS);
        }

        return member;
    }

    // 현재 보유 금액과 레벨업 관계 기능
    public Member levelUp(Long mNo){
        Member member = memberRepository.findById(mNo).orElseThrow(MemberNotExistException::new);
        Integer level = member.getMLevel();
        
        Euro euro = member.getEuro();
        if(euro == null){throw new EuroNotExistException();}
        
        Integer value = euro.getValue();
        
        //levelUp 로직
        Integer levelUpScore = (2000 + 50 * level * (level-1))*10000; // 100
        if(value >= levelUpScore){
            // 레벨업
            member.setMLevel(level+1);
            // 회원 포인트 100만 포인트로 리셋.
            euro.setValue(100 * 10000);
        }

        return member;
    }

    //랭킹 조회 기능

}
