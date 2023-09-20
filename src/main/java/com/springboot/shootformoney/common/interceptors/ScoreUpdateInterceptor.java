package com.springboot.shootformoney.common.interceptors;

import com.springboot.shootformoney.game.service.MatchService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class ScoreUpdateInterceptor implements HandlerInterceptor {
    private final MatchService matchService;
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        matchService.updateAllEndedGames();
//        return true;
//    }
}
