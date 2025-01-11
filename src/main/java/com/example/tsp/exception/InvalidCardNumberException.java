package com.example.tsp.exception;

/**
 * 카드 번호가 유효하지 않을 때 발생하는 예외
 */
public class InvalidCardNumberException extends RuntimeException {

    public InvalidCardNumberException(String message) {
        super(message);
    }

    public InvalidCardNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
