package com.springboot.shootformoney.bet.controllers;

import com.springboot.shootformoney.bet.dto.BetDto;
import com.springboot.shootformoney.bet.entity.Bet;
import com.springboot.shootformoney.bet.service.BetService;
import com.springboot.shootformoney.bet.service.EuroPoolService;
import com.springboot.shootformoney.bet.service.EuroService;
import com.springboot.shootformoney.member.entity.Member;
import com.springboot.shootformoney.member.repository.MemberRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
//import com.springboot.shootformoney.member.dto.MemberInfo;

@RestController
@RequestMapping("/bet")
public class BetController {
    private final BetService betService;
    private final EuroService euroService;

    private final EuroPoolService euroPoolService;

//    private final MemberInfoService memberInfoService;

    @Autowired
    public BetController(BetService betService, EuroService euroService,
            EuroPoolService euroPoolService /* , MemberInfoService memberInfoService*/){
        this.betService = betService;
        this.euroService = euroService;
        this.euroPoolService = euroPoolService;
//        this.memberInfoService = memberInfoService;
    }

    //배팅하는 메서드. Bet 엔티티에 배팅 정보를 추가하고, 보유금에서 배팅금만큼을 감산한다.
    //마지막으로 배팅한 경기의 EuroPool에 해당 예측에 해당하는 배팅금 Pool 값을 증가시킨다.
    @PostMapping("/placebet")
    @Transactional
    public ResponseEntity<String> placeBet(@RequestBody BetDto betDto) {
        try {
            // 배팅 정보 저장
            Bet bet = betService.bet(betDto.getGNo(), betDto.getExpect().toString(), betDto.getBtMoney());

            // EuroPool에 배팅금 누적
            euroPoolService.collectEuro(bet);

            // 유저 정보 가져오기
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();  // 현재 로그인한 사용자의 정보를 가져옴
//            Long mNo = memberInfo.getMNo();  // mNo 값을 읽어옴
            // 유로 보유량 감소 처리
//            euroService.decreaseEuro(mNo, betDto.getBtMoney());

            return new ResponseEntity<>("배팅이 성공적으로 저장되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //배팅 취소 메서드. Bet엔터티에서 배팅 정보를 삭제하고, 보유금에 배팅금만큼 가산한다.
    @PostMapping("/cancelbet/{btNo}")
    @Transactional
    public ResponseEntity<String> cancelBet(@PathVariable Long btNo) {
        try {
            // 배팅 정보 삭제
            Bet cancelBet = betService.betCancel(btNo);

            //EuroPool에 배팅금액 가산.
            euroPoolService.rollbackEuroPool(cancelBet);

            // 유저 정보 가져오기
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();  // 현재 로그인한 사용자의 정보를 가져옴
//            Long mNo = memberInfo.getMNo();  // mNo 값을 읽어옴
            // 보유 유로 롤백 처리
//            euroService.rollbackEuro(mNo, btNo);

            return new ResponseEntity<>("Bet placed and Euro decreased successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //정산 메서드. dividend() 메서드 사용.
    @PostMapping("/dividend")
    public ResponseEntity<Void> calculateDividend() {
        betService.calcBtRatio();
        betService.dividend();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
