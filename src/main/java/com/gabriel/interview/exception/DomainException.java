package com.gabriel.interview.exception;

/**
 * Base exception for all domain-related exceptions in the application.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
    
}
