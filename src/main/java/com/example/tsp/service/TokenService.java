package com.example.tsp.service;

import com.example.tsp.entity.Token;
import com.example.tsp.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    /**
     * 참조 ID 기반 토큰 생성
     *
     * @param refId 카드 참조 ID
     * @return 생성된 토큰 값
     */
    public String generateToken(String refId) {
        if (tokenRepository.existsByRefId(refId)) {
            throw new RuntimeException("이미 생성된 토큰입니다.");
        }

        Token token = new Token();
        token.setRefId(refId);
        token.setTokenValue(UUID.randomUUID().toString());
        token.setIssuedDt(LocalDateTime.now());
        token.setExpireDt(LocalDateTime.now().plusHours(1)); // 1시간 유효

        tokenRepository.save(token);
        return token.getTokenValue();
    }
}
