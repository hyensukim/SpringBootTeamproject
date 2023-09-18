package com.springboot.shootformoney.game.service;

import com.springboot.shootformoney.game.dto.MatchDto;
import com.springboot.shootformoney.game.dto.MatchesDto;
import com.springboot.shootformoney.game.dto.ScoreDto;
import com.springboot.shootformoney.game.dto.data.Match;
import com.springboot.shootformoney.game.entity.Game;
import com.springboot.shootformoney.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/*
* 경기 관련 데이터 조회 및 저장 구현 클래스
* Author : Hyedokal (https://www.github.com/Hyedokal)
*/

@Service
public class MatchService {

    @Value("${football.api.base-url}")
    private String baseUrl;

    @Value(("${football.api.key}"))
    private String apiKey;

    @Autowired
    private GameRepository gameRepository;

    //기간 내의 모든 EPL 경기를 API에서 조회하는 메서드.
    public MatchesDto getPLMatches() {
        LocalDate dateFrom = LocalDate.now();
        //접속일 기준 이후 10일까지의 데이터 조회.
        LocalDate dateTo = dateFrom.plusDays(10);

        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/competitions/2021/matches")
                .queryParam("dateFrom", dateFrom)
                .queryParam("dateTo", dateTo)
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<MatchesDto> responseEntity =
                restTemplate.exchange(uri, HttpMethod.GET, entity, MatchesDto.class);

        return responseEntity.getBody();
    }

    //기간 내의 모든 라리가 경기를 API에서 조회하는 메서드.
    public MatchesDto getPDMatches() {
        LocalDate dateFrom = LocalDate.now();
        //접속일 기준 이후 10일까지의 데이터 조회.
        LocalDate dateTo = dateFrom.plusDays(10);

        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/competitions/2014/matches")
                .queryParam("dateFrom", dateFrom)
                .queryParam("dateTo", dateTo)
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<MatchesDto> responseEntity =
                restTemplate.exchange(uri, HttpMethod.GET, entity, MatchesDto.class);

        return responseEntity.getBody();
    }

    //기간 내의 모든 분데스리가 경기를 API에서 조회하는 메서드.
    public MatchesDto getBL1Matches() {
        LocalDate dateFrom = LocalDate.now();
        //접속일 기준 이후 10일까지의 데이터 조회.
        LocalDate dateTo = dateFrom.plusDays(10);

        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/competitions/2002/matches")
                .queryParam("dateFrom", dateFrom)
                .queryParam("dateTo", dateTo)
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<MatchesDto> responseEntity =
                restTemplate.exchange(uri, HttpMethod.GET, entity, MatchesDto.class);

        return responseEntity.getBody();
    }

    //기간 내의 모든 EPL/라리가/분데스리가 경기를 API에서 조회하는 메서드.
    public List<MatchesDto> getAllMatches() {
        List<MatchesDto> allMatches = new ArrayList<>();

        // EPL 경기 정보 가져오기
        MatchesDto eplMatches = getPLMatches();
        allMatches.add(eplMatches);

        // 라리가 경기 정보 가져오기
        MatchesDto pdMatches = getPDMatches();
        allMatches.add(pdMatches);

        // 분데스리가 경기 정보 가져오기
        MatchesDto bl1matches= getBL1Matches();
        allMatches.add(bl1matches);

        return allMatches;
    }

    //외부 API에서 PL/라리가/분데스 경기 정보를 가져오고 DB에 저장하는 메서드.
    public void saveAllMatchesToDB(){
        MatchesDto plMatches = getPLMatches();
        MatchesDto pdMatches = getPDMatches();
        MatchesDto bl1Matches = getBL1Matches();
        List<Game> games = convertToEntity(plMatches.getMatches());
        games.addAll(convertToEntity(pdMatches.getMatches()));
        games.addAll(convertToEntity(bl1Matches.getMatches()));
        for(Game game : games){
            //중복체크 후 저장
            if(!gameRepository.existsByMatchId(game.getMatchId())){
                gameRepository.save(game);
            }
        }
    }

    //외부 API에서 JSON 형식으로 받아 온 데이터를 DB에 저장할 수 있는 List<Game>으로 변환하는 메서드.
    //saveAllMatchesToDB()에 포함되는 메서드임.
    private List<Game> convertToEntity(List<Match> matches){
        return matches.stream()
                .map(match -> Game.builder()
                        .gLeague(match.getCompetition().getName())
                        .matchId(match.getMatchId())
                        .gHomeTeam(match.getHomeTeam().getName())
                        .gAwayTeam(match.getAwayTeam().getName())
                        .gStartTime(match.getUtcDate())
                        .gHomeScore(match.getScore().getFullTime().getHome())
                        .gAwayScore(match.getScore().getFullTime().getAway())
                        .build())
                .collect(Collectors.toList());
    }


    //경기결과가 나온 PL 경기들의 스코어를 update해 주는 메서드. 경기 시작 후 6시간 뒤에 이루어짐.
    @Transactional
    public void updateEndedPLGames() {
        List<Game> allGames = gameRepository.findAll();
        LocalDateTime now = LocalDateTime.now(); // 현재 시스템 날짜

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = baseUrl + "/competitions/2021/matches?status=FINISHED"; //종료된 PL경기를 API에서 검색.
         //헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        //HttpEntity에 헤더 설정
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<MatchesDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, MatchesDto.class);
        MatchesDto matchesDto = response.getBody();
        for(Match match : matchesDto.getMatches()){
            for(Game game : allGames) {
                if (match.getMatchId().equals(game.getMatchId())) {
                    game.setGHomeScore(match.getScore().getFullTime().getHome());
                    game.setGAwayScore(match.getScore().getFullTime().getAway());
                }
            }
        }
    }

    //경기결과가 나온 라리가 경기들의 스코어를 update해 주는 메서드. 경기 시작 후 6시간 뒤에 이루어짐.
    @Transactional
    public void updateEndedPDGames() {
        List<Game> allGames = gameRepository.findAll();
        LocalDateTime now = LocalDateTime.now(); // 현재 시스템 날짜

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = baseUrl + "/competitions/2014/matches?status=FINISHED"; //종료된 PL경기를 API에서 검색.
        //헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        //HttpEntity에 헤더 설정
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<MatchesDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, MatchesDto.class);
        MatchesDto matchesDto = response.getBody();
        for(Match match : matchesDto.getMatches()){
            for(Game game : allGames) {
                if (match.getMatchId().equals(game.getMatchId())) {
                    game.setGHomeScore(match.getScore().getFullTime().getHome());
                    game.setGAwayScore(match.getScore().getFullTime().getAway());
                }
            }
        }
    }

    //경기결과가 나온 분데스 경기들의 스코어를 update해 주는 메서드. 경기 시작 후 6시간 뒤에 이루어짐.
    @Transactional
    public void updateEndedBL1Games() {
        List<Game> allGames = gameRepository.findAll();
        LocalDateTime now = LocalDateTime.now(); // 현재 시스템 날짜

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = baseUrl + "/competitions/2002/matches?status=FINISHED"; //종료된 PL경기를 API에서 검색.
        //헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        //HttpEntity에 헤더 설정
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<MatchesDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, MatchesDto.class);
        MatchesDto matchesDto = response.getBody();
        for(Match match : matchesDto.getMatches()){
            for(Game game : allGames) {
                if (match.getMatchId().equals(game.getMatchId())) {
                    game.setGHomeScore(match.getScore().getFullTime().getHome());
                    game.setGAwayScore(match.getScore().getFullTime().getAway());
                }
            }
        }
    }

    @Transactional
    public void updateAllEndedGames(){
        updateEndedPLGames();
        updateEndedPDGames();
        updateEndedBL1Games();
    }

    //DB에 저장된 경기목록 중, 시작하지 않은 경기만 조회하는 메서드.
    public List<Game> getAllUnstartedMatches(){
        return gameRepository.findAllUnstartedMatches();
    }

    //DB에 저장된 경기목록 중, 시작하지 않은 PL 경기만 조회하는 메서드.
    public List<Game> getUnstartedPLMatches(){
        return gameRepository.findUnstartedPLMatches();
    }

    //DB에 저장된 경기목록 중, 시작하지 않은 라리가 경기만 조회하는 메서드.
    public List<Game> getUnstartedPDMatches(){
        return gameRepository.findUnstartedPDMatches();
    }

    //DB에 저장된 경기목록 중, 시작하지 않은 분데스 경기만 조회하는 메서드.
    public List<Game> getUnstartedBL1Matches(){
        return gameRepository.findUnstartedBL1Matches();
    }

    //DB에 저장된 경기목록 중, 종료된 PL 경기만 조회하는 메서드.
    public List<Game> getFinishedPLMatches(){
        return gameRepository.findFinishedPLMatches();
    }

    //DB에 저장된 경기목록 중, 종료된 라리가 경기만 조회하는 메서드.
    public List<Game> getFinishedPDMatches(){
        return gameRepository.findFinishedPDMatches();
    }

    //DB에 저장된 경기목록 중, 종료된 분데스 경기만 조회하는 메서드.
    public List<Game> getFinishedBL1Matches(){
        return gameRepository.findFinishedBL1Matches();
    }

    //DB에 저장된 경기목록 중, 종료된 분데스 경기만 조회하는 메서드.
    public List<Game> getAllFinishedMatches(){
        return gameRepository.findAllFinishedMatches();
    }

    //경기 상세정보 조회하기 메서드(API에서 받아온 matchId로 조회)
    public Optional<Game> getGameByMatchId(Integer matchId){
        return gameRepository.findByMatchId(matchId);
    }
}
