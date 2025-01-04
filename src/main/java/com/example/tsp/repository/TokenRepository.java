package com.example.tsp.repository;

import com.example.tsp.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    boolean existsByRefId(String refId);
    Token findByTokenValue(String tokenValue);
}
