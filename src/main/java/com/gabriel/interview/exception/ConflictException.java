package com.gabriel.interview.exception;

/**
 * Exception thrown when a resource conflict occurs (e.g., duplicate entries).
 * Results in HTTP 409 CONFLICT response.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class ConflictException extends DomainException {
    public ConflictException(String message) {
        super(message);
    }
}
