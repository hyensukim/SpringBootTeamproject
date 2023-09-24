package com.springboot.shootformoney.common.interceptors;

import com.springboot.shootformoney.member.dto.MemberInfo;
import com.springboot.shootformoney.member.entity.Euro;
import com.springboot.shootformoney.member.entity.LoginData;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.EuroRepository;
import com.springboot.shootformoney.member.repository.MemberRepository;
import com.springboot.shootformoney.member.utils.LevelGradeUtil;
import com.springboot.shootformoney.member.utils.MemberUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.util.Optional;

@Component("memberUpdate")
@RequiredArgsConstructor
public class MemberUpdateInterceptor implements HandlerInterceptor {

    private final LevelGradeUtil levelRankUtil;
    private final MemberUtil memberUtil;
    private final MemberRepository memberRepository;
    private final EuroRepository euroRepository;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if(memberUtil.isLogin()){
            MemberInfo memberInfo = memberUtil.getMember();
            Long mNo = memberInfo.getMNo();
            Member member = null;
            LoginData loginData = null;
            String nickname = "";
            Integer level= 0;
            String formattedEuro = "";

            Optional<Member> om = memberRepository.findById(mNo);
            if(om.isPresent()){
                member = om.get();
            }

            Euro euro = euroRepository.findBymNo(mNo);

            if(member != null){
                member = levelRankUtil.levelUp(mNo);
                member = levelRankUtil.gradeUp(member);
                loginData = member.getLoginData();
                level = member.getMLevel();
                nickname = member.getMNickName();
            }

            if(euro != null){
                formattedEuro = String.format("%,d",euro.getValue());
            }

            req.setAttribute("mNo",mNo);
            req.setAttribute("nickname",nickname);
            req.setAttribute("formattedEuro",formattedEuro);
            req.setAttribute("level",level);


            if(loginData == null && member!= null){
                loginData = new LoginData();
                loginData.setLoginDate(LocalDateTime.now());
                member.setLoginData(loginData);
            }else if(loginData != null){
                loginData.setLoginDate(LocalDateTime.now());
            }

            if(member != null) {
                memberRepository.saveAndFlush(member);
            }
        }
        return true;
    }
}
