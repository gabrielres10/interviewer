package com.gabriel.interview.exception;

/**
 * Exception thrown when attempting to create a duplicate topic.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class DuplicateTopicException extends ConflictException {
    public DuplicateTopicException(String message) {
        super(message);
    }
    
}
