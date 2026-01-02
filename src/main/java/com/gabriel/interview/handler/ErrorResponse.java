package com.gabriel.interview.handler;

import java.time.LocalDateTime;

/**
 * Error response object for the global exception handler.
 * Provides structured error information to API clients.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int statusCode;
    private String message;
    //private String path;

    public ErrorResponse(int statusCode, String message) {
        this.timestamp = LocalDateTime.now();
        this.statusCode = statusCode;
        this.message = message;
        //this.path = path;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
