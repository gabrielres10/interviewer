package com.gabriel.interview.exception;

/**
 * Exception thrown when input data is invalid.
 * Results in HTTP 400 BAD_REQUEST response.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class InvalidInputException extends DomainException {
    public InvalidInputException(String message) {
        super(message);
    }
    
}
