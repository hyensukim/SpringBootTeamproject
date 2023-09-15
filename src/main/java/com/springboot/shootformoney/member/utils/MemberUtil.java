package com.springboot.shootformoney.member.utils;

import com.springboot.shootformoney.member.dto.MemberInfo;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.enum_.Role;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;

    /**
     * 로그인 여부 확인
     */
    public boolean isLogin(){
        return getMember() != null;
    }

    public boolean isAdmin(){
        return isLogin() && getMember().getRole() == Role.ADMIN;
    }

    public MemberInfo getMember(){
        return (MemberInfo) session.getAttribute("memberInfo");
    }

    public Member getEntity(){
        if(isLogin()){
            MemberInfo  memberInfo = getMember();
            return Member.builder()
                    .mNo(memberInfo.getMNo())
                    .mId(memberInfo.getMId())
                    .mPassword(memberInfo.getMPassword())
                    .mName(memberInfo.getMName())
                    .mPhone(memberInfo.getMPhone())
                    .mEmail(memberInfo.getMEmail())
                    .role(memberInfo.getRole())
                    .grade(memberInfo.getGrade())
                    .mLevel(memberInfo.getLevel())
                    .build();
        }
        return null;
    }
}
