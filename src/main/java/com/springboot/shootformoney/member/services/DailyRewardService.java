package com.springboot.shootformoney.member.services;

import com.springboot.shootformoney.member.entity.Euro;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.EuroRepository;

import java.time.LocalDateTime;

public class DailyRewardService {

    private EuroRepository euroRepository;

    public void checkDailyReward(Member member){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastLogin = member.getLoginData().getLastLoginDate();

        if(lastLogin == null || now.isAfter(lastLogin.plusDays(1))){
            giveReward(member);
            member.getLoginData().setLastLoginDate(now);
        }
    }

    public void giveReward(Member member){
        Euro euro = euroRepository.findByMember(member);
        Integer value = euro.getValue();
        euro.setValue(value + 20*10000);
        euroRepository.saveAndFlush(euro);
    }
}