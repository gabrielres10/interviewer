package com.gabriel.interview.exception;

/**
 * Exception thrown when topic data is invalid.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class InvalidTopicException extends InvalidInputException {
    public InvalidTopicException(String message) {
        super(message);
    }
    
}
