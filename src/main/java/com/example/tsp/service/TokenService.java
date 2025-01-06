package com.example.tsp.service;

import com.example.tsp.entity.Token;
import com.example.tsp.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public String generateToken(String refId) {
        // 중복 토큰 생성 방지
//        if (tokenRepository.existsByRefIdAndUsed(refId, false)) {
//            throw new RuntimeException("이미 생성된 활성 토큰이 있습니다. 관리자에게 문의주세요.");
//        }

        // 토큰 생성
        // 결제 시마다 Token 테이블에 row가 추가됨
        // 1회용 토큰 만료시 삭제 정책 필요
        Token token = new Token();
        token.setRefId(refId);
        token.setTokenValue(UUID.randomUUID().toString()); // unique
        token.setIssuedDt(LocalDateTime.now());
        token.setExpireDt(LocalDateTime.now().plusMinutes(1)); // 1분유효
        token.setUsed(false); // 초기 상태는 사용되지 않음

        tokenRepository.save(token);

        return token.getTokenValue();
    }

    public boolean verifyToken(String tokenValue) {
        Optional<Token> tokenOptional = tokenRepository.findByTokenValue(tokenValue);

        if (tokenOptional.isEmpty()) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        Token token = tokenOptional.get();

        // 만료 여부 확인
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

        return true;
    }
}
