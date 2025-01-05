package com.example.tsp.repository;

import com.example.tsp.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenValue(String tokenValue);
    boolean existsByRefIdAndUsed(String refId, boolean used);
}
