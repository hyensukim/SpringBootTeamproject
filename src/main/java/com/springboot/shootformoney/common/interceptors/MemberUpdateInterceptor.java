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

import java.time.Duration;
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
            Optional<Member> om = memberRepository.findById(mNo);
            if(om.isPresent()){
                member = om.get();
            }
            Euro euro = euroRepository.findBymNo(mNo);
            member = levelRankUtil.levelUp(mNo);
            member = levelRankUtil.gradeUp(member);
            LoginData loginData = member.getLoginData();
            Integer level = member.getMLevel();
            String nickname = member.getMNickName();
            String formattedEuro = "";
            String remain = null;


            if(euro != null){
                formattedEuro = String.format("%,d",euro.getValue());
            }

            if(loginData == null){
                loginData = new LoginData();
                loginData.setLoginDate(LocalDateTime.now());
                member.setLoginData(loginData);
            }else{
                loginData.setLoginDate(LocalDateTime.now());
            }

            /* 출석 이벤트 후 이벤트 받기 까지 남은 시간 S */
            if(loginData.getLastLoginDate() != null) {
                LocalDateTime nextTime = loginData.getLastLoginDate().plusDays(1);
                LocalDateTime now = LocalDateTime.now();
                Duration duration = Duration.between(nextTime, now).abs();
                long sec = duration.getSeconds();

                long hours = sec / 3600;
                long minutes = ((sec % 3600) / 60);

                if (now.isBefore(nextTime)) {
                    remain = String.format("다음 출석까지 %02d시 %02d분 남았습니다.", hours, minutes);
                }
            }
            /* 출석 이벤트 후 이벤트 받기 까지 남은 시간 E */

            req.setAttribute("nickname",nickname);
            req.setAttribute("remain",remain);
            req.setAttribute("mNo",mNo);
            req.setAttribute("formattedEuro",formattedEuro);
            req.setAttribute("level",level);

            memberRepository.saveAndFlush(member);
        }
        return true;
    }
}
