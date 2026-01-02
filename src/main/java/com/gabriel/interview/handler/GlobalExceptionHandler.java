package com.gabriel.interview.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gabriel.interview.exception.BusinessRuleViolationException;
import com.gabriel.interview.exception.ConflictException;
import com.gabriel.interview.exception.InvalidInputException;
import com.gabriel.interview.exception.ResourceNotFoundException;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.converter.HttpMessageNotReadableException;


/**
 * Global exception handler for the application.
 * Catches and handles exceptions thrown by controllers and services,
 * converting them into appropriate HTTP responses.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Handles Jakarta Bean Validation exceptions.
     * Returns the first validation error message found.
     * 
     * @param ex The validation exception
     * @return ErrorResponse with BAD_REQUEST status and error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        String output = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request");

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), output);
    }

    /**
     * Handles HTTP message not readable exceptions.
     * Occurs when the request body cannot be parsed.
     * 
     * @param ex The HttpMessageNotReadableException
     * @return ErrorResponse with BAD_REQUEST status
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String output = ex.getMessage();
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), output);
    }
    
    /**
     * Handles ResourceNotFoundException and its subclasses.
     * Returns a 404 NOT_FOUND status.
     * 
     * @param ex The ResourceNotFoundException
     * @return ErrorResponse with NOT_FOUND status and error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    /**
     * Handles InvalidInputException.
     * Returns a 400 BAD_REQUEST status for invalid input data.
     * 
     * @param ex The InvalidInputException
     * @return ErrorResponse with BAD_REQUEST status and error message
     */
    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleInvalidInput (InvalidInputException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    /**
     * Handles ConflictException.
     * Returns a 409 CONFLICT status for resource conflicts.
     * 
     * @param ex The ConflictException
     * @return ErrorResponse with CONFLICT status and error message
     */
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleConflict (ConflictException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    /**
     * Handles BusinessRuleViolationException.
     * Returns a 422 UNPROCESSABLE_CONTENT status for business rule violations.
     * 
     * @param ex The BusinessRuleViolationException
     * @return ErrorResponse with UNPROCESSABLE_CONTENT status and error message
     */
    @ExceptionHandler(BusinessRuleViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public @ResponseBody ErrorResponse handleBusinessRuleViolation (BusinessRuleViolationException ex) {
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_CONTENT.value(), ex.getMessage());
    }

    /**
     * Handles all uncaught exceptions.
     * Returns a 500 INTERNAL_SERVER_ERROR status as a fallback.
     * 
     * @param ex The unhandled exception
     * @return ErrorResponse with INTERNAL_SERVER_ERROR status and error message
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleDefaultException (Exception ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
