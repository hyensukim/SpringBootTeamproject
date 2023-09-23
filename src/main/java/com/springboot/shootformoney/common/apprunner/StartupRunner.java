package com.springboot.shootformoney.common.apprunner;

import com.springboot.shootformoney.bet.service.BetService;
import com.springboot.shootformoney.bet.service.EuroPoolService;
import com.springboot.shootformoney.game.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupRunner implements ApplicationRunner {
    private final MatchService matchService;
    private final BetService betService;
    private final EuroPoolService euroPoolService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 경기정보 저장
//        matchService.saveAllMatchesToDB();
//        matchService.updateAllEndedGames();
        // BetService의 정산 메서드 실행
        betService.calcBtRatio();
        betService.dividend();
        //EuroPoolService의 경기정보 저장 메서드 실행
        euroPoolService.addEuroPools();
    }
}
