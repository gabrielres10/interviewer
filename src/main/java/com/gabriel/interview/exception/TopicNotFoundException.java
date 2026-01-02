package com.gabriel.interview.exception;

/**
 * Exception thrown when a topic is not found.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class TopicNotFoundException extends ResourceNotFoundException {
    public TopicNotFoundException(String message) {
        super(message);
    }
    
}
