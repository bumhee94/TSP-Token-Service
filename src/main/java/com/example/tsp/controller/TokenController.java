package com.example.tsp.controller;

import com.example.tsp.service.RefIdService;
import com.example.tsp.service.TokenService;
import com.example.tsp.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tsp")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RefIdService refIdService;

    @Autowired
    private EncryptionUtil encryptionUtil;

    /**
     * 참조ID 생성 API
     *
     * @param encryptCardNumber 암호화된 카드 번호
     * @return 생성된 참조 ID
     */
    @PostMapping("/generate-ref-id")
    public ResponseEntity<String> generateRefId(@RequestBody String encryptCardNumber) {
        try {
            // 넘어온 암호화된 카드 번호 복호화
            String cardNumber = encryptionUtil.decrypt(encryptCardNumber);

            // 참조 ID 생성
            String refId = refIdService.generateRefId(cardNumber);
            return ResponseEntity.ok(refId);
        } catch (RuntimeException e) {
            // 유효하지 않은 카드 번호 처리
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("참조 ID 생성 중 알 수 없는 오류가 발생했습니다.");
        }
    }

    /**
     * 토큰 생성 API
     *
     * @param refId 카드 참조 ID
     * @return 생성된 토큰 값
     */
    @PostMapping("/generate-token")
    public ResponseEntity<String> generateToken(@RequestBody String refId) {
        try {
            String token = tokenService.generateToken(refId);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("토큰 생성 중 알 수 없는 오류가 발생했습니다.");
        }
    }

    /**
     * 토큰 검증 API
     *
     * @param tokenValue 토큰 값
     * @return true or false
     */
    @PostMapping("/verify-token")
    public ResponseEntity<Boolean> verifyToken(@RequestBody String tokenValue) {
        try {
            boolean isValid = tokenService.verifyToken(tokenValue);
            return ResponseEntity.ok(isValid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false);
        }
    }
}
