package com.example.tsp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String refId; // TR에서 전달받은 카드 참조 ID

    @Column(nullable = false, unique = true)
    private String tokenValue; // 생성된 토큰 값

    @Column(nullable = false)
    private LocalDateTime issuedDt; // 토큰 발급 시간

    @Column(nullable = false)
    private LocalDateTime expireDt; // 토큰 만료 시간
}
