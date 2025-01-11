package com.example.tsp.controller;

import com.example.tsp.service.RefIdService;
import com.example.tsp.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tsp")
public class TokenController {

    private final TokenService tokenService;
    private final RefIdService refIdService;

    /**
     * 참조ID 생성 API
     *
     * @param cardNumber 카드 번호
     * @return 생성된 참조 ID
     */
    @PostMapping("/generate-ref-id")
    public ResponseEntity<String> generateRefId(@RequestBody String cardNumber) {
        String refId = refIdService.generateRefId(cardNumber);
        return ResponseEntity.ok(refId);
    }

    /**
     * 토큰 생성 API
     *
     * @param cardRefId 카드 참조 ID
     * @return 생성된 토큰
     */
    @PostMapping("/generate-token")
    public ResponseEntity<String> generateToken(@RequestBody String cardRefId) {
        String token = tokenService.generateToken(cardRefId);
        return ResponseEntity.ok(token);
    }

    /**
     * 토큰 유효성 검증 API
     *
     * @param tokenValue 토큰 값
     * @return 유효 여부
     */
    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestParam String tokenValue) {
        boolean isValid = tokenService.verifyToken(tokenValue);
        return ResponseEntity.ok(isValid);
    }
}
