package com.gabriel.interview.exception;

/**
 * Exception thrown when a requested resource is not found.
 * Results in HTTP 404 NOT_FOUND response.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class ResourceNotFoundException extends DomainException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
