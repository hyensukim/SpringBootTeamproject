package com.springboot.shootformoney.bet.controllers;

import com.springboot.shootformoney.bet.dto.BetDto;
import com.springboot.shootformoney.bet.entity.Bet;
import com.springboot.shootformoney.bet.service.BetService;
import com.springboot.shootformoney.bet.service.EuroPoolService;
import com.springboot.shootformoney.bet.service.EuroService;
import com.springboot.shootformoney.game.service.MatchService;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import com.springboot.shootformoney.member.utils.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import com.springboot.shootformoney.member.dto.MemberInfo;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bet")
public class BetController {
    private final BetService betService;
    private final EuroService euroService;
    private final EuroPoolService euroPoolService;
    private final MemberUtil memberUtil;
    private final MatchService matchService;


    //배팅하는 메서드. Bet 엔티티에 배팅 정보를 추가하고, 보유금에서 배팅금만큼을 감산한다.
    //마지막으로 배팅한 경기의 EuroPool에 해당 예측에 해당하는 배팅금 Pool 값을 증가시킨다.
    @PostMapping("/placebet")
    @Transactional
    public String placeBet(BetDto betDto, Model model) {
        model.addAttribute("pageTitle", "배팅 등록하기");
        // 유저 정보 가져오기
        Long mNo = memberUtil.getMember().getMNo();
        betDto.setGNo(matchService.getGameInfo(betDto.getMatchId())
                .orElseThrow().getGNo());
        betDto.setMNo(mNo);
        // 배팅 정보 저장
        Bet bet = betService.bet(betDto.getGNo(), betDto.getExpect(), betDto.getBtMoney());
        // EuroPool에 배팅금 누적
        euroPoolService.collectEuro(bet);
        // 유로 보유량 감소 처리
        euroService.decreaseEuro(mNo, betDto.getBtMoney());
        return "redirect:/list/unstarted/entirelist";
    }

    //배팅 취소 메서드. Bet엔터티에서 배팅 정보를 삭제하고, 보유금에 배팅금만큼 가산한다.
    @PostMapping("/cancelbet/{btNo}")
    @Transactional
    public String cancelBet(@PathVariable Long btNo, Model model) {
        model.addAttribute("pageTitle", "배팅 취소하기");
        try {
            // 배팅 정보 삭제
            Bet cancelBet = betService.betCancel(btNo);

            //EuroPool에 배팅금액 가산.
            euroPoolService.rollbackEuroPool(cancelBet);

            // 유저 정보 가져오기
            Long mNo = memberUtil.getMember().getMNo();
            // 보유 유로 롤백 처리
            euroService.rollbackEuro(mNo, btNo);

            return "redirect:/list/unstarted/entirelist";
        } catch (Exception e) {
            return "배팅 취소 중 오류가 발생했습니다.";
        }
    }

}
