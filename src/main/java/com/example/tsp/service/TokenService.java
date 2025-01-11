package com.example.tsp.service;

import com.example.tsp.entity.Token;
import com.example.tsp.exception.InvalidTokenException;
import com.example.tsp.repository.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    @Transactional
    public String generateToken(String refId) {
        // 토큰 생성
        // 결제 시마다 Token 테이블에 row가 추가됨
        // 1회용 토큰 만료시 삭제 정책 필요
        Token token = Token.builder()
                .refId(refId)
                .tokenValue(UUID.randomUUID().toString()) // unique
                .issuedDt(LocalDateTime.now())
                .expireDt(LocalDateTime.now().plusMinutes(1)) // 1분유효
                .used(false) // 초기 상태는 사용되지 않음
                .build();

        tokenRepository.save(token);

        return token.getTokenValue();
    }

    public boolean verifyToken(String tokenValue) {
        // 토큰 존재 여부 확인
        Token token = tokenRepository.findByTokenValue(tokenValue)
                .orElseThrow(() -> new InvalidTokenException("유효하지 않은 토큰입니다."));

        // 토큰 만료 여부 확인
        if (token.getExpireDt().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("토큰이 만료되었습니다.");
        }

        // 토큰 사용 여부 확인
        if (token.getUsed()) {
            throw new InvalidTokenException("이미 사용된 토큰입니다.");
        }

        // 토큰 사용 처리
        token.markAsUsed();
        tokenRepository.save(token);

        return true;
    }
}
