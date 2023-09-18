package com.springboot.shootformoney.member.services;

import com.springboot.shootformoney.member.dto.MemberInfo;
import com.springboot.shootformoney.member.entity.Euro;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.EuroRepository;
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

    public void checkDailyReward(MemberInfo memberInfo){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastLogin = memberInfo.getLoginData().getLastLoginDate();

        if(lastLogin == null || now.isAfter(lastLogin.plusDays(1))){
            giveReward(memberInfo);
            memberInfo.getLoginData().setLastLoginDate(now);
        }
    }

    public void giveReward(MemberInfo memberInfo){
        Long mNo = memberInfo.getMNo();
        Euro euro = euroRepository.findBymNo(mNo);
        Integer value = euro.getValue();
        euro.setValue(value + 20*10000);
        euroRepository.saveAndFlush(euro);
    }
}