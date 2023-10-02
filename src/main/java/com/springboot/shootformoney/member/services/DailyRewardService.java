package com.springboot.shootformoney.member.services;

import com.springboot.shootformoney.member.dto.MemberInfo;
import com.springboot.shootformoney.member.entity.Euro;
import com.springboot.shootformoney.member.entity.LoginData;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.exceptions.MemberNotExistException;
import com.springboot.shootformoney.member.repository.EuroRepository;
import com.springboot.shootformoney.member.repository.MemberRepository;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class DailyRewardService {

    private final EuroRepository euroRepository;
    private final MemberRepository memberRepository;

    public void checkDailyReward(Long mNo){
        Member member = memberRepository.findById(mNo).orElseThrow(MemberNotExistException::new);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime loginDate = member.getLoginData().getLoginDate();
        LocalDateTime lastLoginData = member.getLoginData().getLastLoginDate();
        if(loginDate != null){
            if(lastLoginData == null || loginDate.isAfter(lastLoginData.plusDays(1))) {
                giveReward(mNo);
                member.getLoginData().setLastLoginDate(now);
                memberRepository.save(member);
            }
            if (lastLoginData != null && loginDate.isBefore(lastLoginData.plusDays(1))) {
                throw new RuntimeException("출석 포인트를 이미 지급 받았습니다.");
            }
        }
    }

    public void giveReward(Long mNo){
        Euro euro = euroRepository.findBymNo(mNo);
        euro.setValue(euro.getValue() + 20*10000);
        euroRepository.saveAndFlush(euro);
    }
}