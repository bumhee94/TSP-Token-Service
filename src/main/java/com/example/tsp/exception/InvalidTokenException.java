package com.example.tsp.exception;

/**
 * 토큰이 없거나 만료되었을 때 발생하는 예외
 */
public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
