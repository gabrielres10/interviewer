package com.gabriel.interview.exception;

/**
 * Exception thrown when attempting to create a duplicate question.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class DuplicateQuestionException extends ConflictException {
    public DuplicateQuestionException(String message) {
        super(message);
    }
}
