package com.springboot.shootformoney.member.utils;

import com.springboot.shootformoney.bet.entity.Bet;
import com.springboot.shootformoney.bet.repository.BetRepository;
import com.springboot.shootformoney.member.entity.Euro;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.enum_.Grade;
import com.springboot.shootformoney.member.exceptions.EuroNotExistException;
import com.springboot.shootformoney.member.exceptions.MemberNotExistException;
import com.springboot.shootformoney.member.repository.EuroRepository;
import com.springboot.shootformoney.member.repository.MemberRepository;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LevelRankUtil {

    private final BetRepository betRepository;
    private final MemberRepository memberRepository;
    private final EuroRepository euroRepository;

    // 회원에 누적 배팅 금액 설정(배팅 누적액 - 랭킹에 반영)
    public Member getMemberWithStack(Long mNo){
        List<Bet> bets = betRepository.findBymNo(mNo);
        Long sum = 0L;
        for(Bet b : bets){
            sum += b.getBtMoney();
        }
        Member member = memberRepository.findById(mNo).orElseThrow(MemberNotExistException::new);
        member.setMStack(sum);
        return memberRepository.saveAndFlush(member);
    }

    // 현재 보유 금액과 레벨업 관계 기능
    /* setStack(mNo) 관련 메서드명 수정 - 사용 용도가 직관적이도록 변경
        NullPointerException 처리해주기
        Integer 형태를 Long 으로 변형
        레벨업 결과 여부를 사용자가 확인할 수 있도록 구현
    */
    @Transactional
    public void levelUp(Long mNo){
        Member member = memberRepository.findById(mNo).orElseThrow(MemberNotExistException::new);
        Integer level = member.getMLevel();

        Euro euro = euroRepository.findBymNo(mNo);
        if(euro == null){
            throw new EuroNotExistException();
        }

        Integer value = euro.getValue();
        Integer levelUpScore = (2000 + 50 * level * (level-1))*10000; // 100

        if(value >= levelUpScore){
            // 레벨업
            member.setMLevel(level+1);
            memberRepository.save(member);

            // 회원 포인트 100만 포인트로 리셋.
            euro.setValue(100 * 10000);
            euroRepository.save(euro);
        }
    }

    public void gradeUp(Long mNo){
        Member member = getMemberWithStack(mNo);
        if(member == null) {
            throw new MemberNotExistException();
        }
        Long stack = member.getMStack();

        if(stack >= 26000*10000){
            member.setGrade(Grade.EUROPA);
        }

        if (stack >= 52000 * 10000) {
            member.setGrade(Grade.CHAMPIONS);
        }

        if(member.getGrade() != Grade.CONFERENCE) {
            memberRepository.saveAndFlush(member);
        }
    }
}
