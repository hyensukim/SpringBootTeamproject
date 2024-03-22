package com.springboot.shootformoney.member.controllers;

import com.springboot.shootformoney.member.services.DailyRewardService;
import com.springboot.shootformoney.member.utils.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class DailyRewardController {

    private final DailyRewardService dailyRewardService;
    private final MemberUtil memberUtil;

    @GetMapping("/reward")
    public String dailyReward(Model model){
        model.addAttribute("pageTitle","출석 보상");
        try {
            if (memberUtil.isLogin()) {
                Long mNo = memberUtil.getMember().getMNo();
                dailyRewardService.checkDailyReward(mNo);

                String script = String.format("Swal.fire('※출석 이벤트 : 20만 포인트가 추가되었습니다.','','success')" +
                        ".then(function(){history.back();})");
                model.addAttribute("script", script);
                return "script/sweet";
            }
        }catch(Exception e){
            String script = String.format("Swal.fire('%s','','error')" +
                    ".then(function(){history.back();})",e.getMessage());
            model.addAttribute("script", script);
            return "script/sweet";
        }

        String script = String.format("Swal.fire('로그인 후에 버튼 눌러주세요.','','error')" +
                ".then(function(){location.href='/member/login'})");
        model.addAttribute("script",script);
        return "script/sweet";
    }
}