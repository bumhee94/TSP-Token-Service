package com.example.tsp.controller;

import com.example.tsp.service.RefIdService;
import com.example.tsp.service.TokenService;
import com.example.tsp.service.TokenValidationService;
import com.example.tsp.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tsp")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RefIdService refIdService;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private TokenValidationService tokenValidationService;

    /**
     * 참조ID 생성 api
     *
     * @param encryptCardNumber
     * @return 생성된 참조ID
     */
    @PostMapping("/generate-ref-id")
    public String generateRefId(@RequestBody String encryptCardNumber) {

        // 넘어온 암호화 된 카드번호 복호화 하여 refId 리턴
        String cardNumber = encryptionUtil.decrypt(encryptCardNumber);
        return refIdService.generateRefId(cardNumber);
    }
    /**
     * 토큰 생성 API
     *
     * @param refId 카드 참조 ID
     * @return 생성된 토큰 값
     */
    @PostMapping("/generate-token")
    public ResponseEntity<String> generateToken(@RequestParam String refId) {
        String token = tokenService.generateToken(refId);
        return ResponseEntity.ok(token);
    }

    /**
     * 토큰 검증 API
     *
     * @param tokenValue
     * @return true or false
     */
    @PostMapping("/verify-token")
    public ResponseEntity<Boolean> verifyToken(@RequestBody String tokenValue) {
        boolean isValid = tokenService.verifyToken(tokenValue);
        return ResponseEntity.ok(isValid);
    }
}
