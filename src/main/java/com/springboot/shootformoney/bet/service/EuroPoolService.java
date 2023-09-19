package com.springboot.shootformoney.bet.service;

import com.springboot.shootformoney.bet.entity.Bet;
import com.springboot.shootformoney.bet.entity.EuroPool;
import com.springboot.shootformoney.bet.repository.EuroPoolRepository;
import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.game.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//경기마다 걸린 배팅금 데이터를 저장하는 EuroPool 엔터티의 비즈니스 로직을 담당하는 서비스 클래스.
@Service
public class EuroPoolService {
    private final GameRepository gameRepository;
    private final EuroPoolRepository euroPoolRepository;
    @Autowired
    public EuroPoolService(GameRepository gameRepository, EuroPoolRepository euroPoolRepository) {
        this.gameRepository = gameRepository;
        this.euroPoolRepository = euroPoolRepository;
    }

    //실시간 배당률 정보를 프론트에 제공하기 위한 메서드이다.
    public List<Double> calculateRate(Integer matchId) {

        Game game = gameRepository.findByMatchId(matchId)
                .orElseThrow(()->new RuntimeException("해당 경기 정보가 존재하지 않습니다."));
        EuroPool euroPool = euroPoolRepository.findByGame_gNo(game.getGNo());
        double totalEuro = (double) euroPool.getWinEuro() + euroPool.getDrawEuro() + euroPool.getLoseEuro();
        double winRatio =  Math.round(totalEuro / (double) euroPool.getWinEuro() * 100) / 100.0;
        double drawRatio =  Math.round(totalEuro / (double) euroPool.getDrawEuro() * 100) / 100.0;
        double loseRatio =  Math.round(totalEuro / (double) euroPool.getLoseEuro() * 100) / 100.0;
        List<Double> results = new ArrayList<>();
        results.add(winRatio);
        results.add(drawRatio);
        results.add(loseRatio);
        return results;
    }

    //Game 엔티티의 경기 정보를 받아 와서 미리 EuroPool 엔티티에 저장한다.
    @Transactional
    public void addEuroPools() {
        List<Game> games = gameRepository.findAll();
        for (Game game : games) {
            Long gNo = game.getGNo();
            //중복체크 후 저장
            if(gNo != null && !euroPoolRepository.existsByGame_gNo(gNo)){
                EuroPool euroPool = EuroPool.builder()
                    .game(game)
                    //default가 0으로 설정되어 있지만, 코드의 명확성과 ORM 동작방식으로 고려하여 명시적으로 초기화함.
                    .winEuro(0)
                    .drawEuro(0)
                    .loseEuro(0)
                    .build();
                euroPoolRepository.save(euroPool);
            }
        }
    }

    //고객이 배팅을 완료하면 해당 경기의 예상결과에 따른 배팅금을 누적시키는 메서드.
    @Transactional
    public void collectEuro(Bet bet) {
        // g_no에 해당하는 euroPool 레코드를 찾는다.
        EuroPool euroPool = euroPoolRepository.findByGame_gNo(bet.getGame().getGNo());

        if (euroPool == null) {
            throw new IllegalArgumentException("해당 경기의 데이터가 존재하지 않습니다.");
        }

        switch (bet.getExpect()) {
            case WIN:
                //승리를 예측했다면 승리 쪽 배팅금을 누적시킨다.
                euroPool.setWinEuro(euroPool.getWinEuro() + bet.getBtMoney());
                break;
            case DRAW:
                euroPool.setDrawEuro(euroPool.getDrawEuro() + bet.getBtMoney());
                break;
            case LOSE:
                euroPool.setLoseEuro(euroPool.getLoseEuro() + bet.getBtMoney());
                break;
        }

        // EuroPool 엔티티를 업데이트한다.
        euroPoolRepository.save(euroPool);
    }

    //고객이 배팅을 취소하면 해당 경기의 예상 결과에 따른 배팅금액을 감소시키는 메서드.
    @Transactional
    public void rollbackEuroPool(Bet bet){
        // g_no에 해당하는 euroPool 레코드를 찾는다.
        EuroPool euroPool = euroPoolRepository.findByGame_gNo(bet.getGame().getGNo());

        if (euroPool == null) {
            throw new IllegalArgumentException("해당 경기의 데이터가 존재하지 않습니다.");
        }

        switch (bet.getExpect()) {
            case WIN:
                //승리를 예측했었다면 승리 쪽 배팅금을 감소시킨다.
                euroPool.setWinEuro(euroPool.getWinEuro() - bet.getBtMoney());
                break;
            case DRAW:
                euroPool.setDrawEuro(euroPool.getDrawEuro() - bet.getBtMoney());
                break;
            case LOSE:
                euroPool.setLoseEuro(euroPool.getLoseEuro() - bet.getBtMoney());
                break;
        }

        // EuroPool 엔티티를 업데이트한다.
        euroPoolRepository.save(euroPool);
    }
}
