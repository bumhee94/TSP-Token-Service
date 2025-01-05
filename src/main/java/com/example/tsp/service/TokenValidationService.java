package com.example.tsp.service;

import com.example.tsp.entity.Token;
import com.example.tsp.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenValidationService {

    @Autowired
    private TokenRepository tokenRepository;

    public void validateAndUseToken(String tokenValue) {
        // 토큰 조회
        Token token = tokenRepository.findByTokenValue(tokenValue)
                .orElseThrow(() -> new RuntimeException("유효하지 않은 토큰입니다."));

        // 만료 시간 확인
        if (token.getExpireDt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("토큰이 만료되었습니다.");
        }

        // 사용 여부 확인
        if (token.getUsed()) {
            throw new RuntimeException("이미 사용된 토큰입니다.");
        }

        // 토큰 사용 처리
        token.setUsed(true);
        tokenRepository.save(token);
    }
}
