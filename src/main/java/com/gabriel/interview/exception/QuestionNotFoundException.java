package com.gabriel.interview.exception;

/**
 * Exception thrown when a question with the specified ID is not found.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class QuestionNotFoundException extends ResourceNotFoundException {
    public QuestionNotFoundException(Long id) {
        super("Question with id " + id + " was not found");
    }
}