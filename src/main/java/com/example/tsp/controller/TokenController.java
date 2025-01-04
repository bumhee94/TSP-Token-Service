package com.example.tsp.controller;

import com.example.tsp.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tsp")
public class TokenController {

    @Autowired
    private TokenService tokenService;

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
}
