package com.gabriel.interview.exception;

/**
 * Exception thrown when question data is invalid.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class InvalidQuestionException extends InvalidInputException {
    public InvalidQuestionException(String message) {
        super(message);
    }
}
