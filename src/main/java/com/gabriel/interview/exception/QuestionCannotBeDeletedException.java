package com.gabriel.interview.exception;

/**
 * Exception thrown when a question cannot be deleted due to business rules.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class QuestionCannotBeDeletedException extends BusinessRuleViolationException {
    public QuestionCannotBeDeletedException(String message) {
        super(message);
    }
}
