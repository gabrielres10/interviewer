package com.gabriel.interview.exception;

/**
 * Exception thrown when a business rule is violated.
 * Results in HTTP 422 UNPROCESSABLE_CONTENT response.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class BusinessRuleViolationException extends DomainException {
    public BusinessRuleViolationException(String message) {
        super(message);
    }
}
