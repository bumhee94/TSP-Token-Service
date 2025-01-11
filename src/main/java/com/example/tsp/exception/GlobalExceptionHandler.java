package com.example.tsp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * InvalidCardNumberException 처리
     * @param ex InvalidCardNumberException 인스턴스
     * @return 에러 메시지와 HTTP 상태
     */
    @ExceptionHandler(InvalidCardNumberException.class)
    public ResponseEntity<String> handleInvalidCardNumberException(InvalidCardNumberException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * TokenNotFoundException 처리
     * @param ex TokenNotFoundException 인스턴스
     * @return 에러 메시지와 HTTP 상태
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleTokenNotFoundException(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * 기타 예외 처리
     * @param ex Exception 인스턴스
     * @return 에러 메시지와 HTTP 상태
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}
