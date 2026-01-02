package com.gabriel.interview.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.mock.http.MockHttpInputMessage;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.gabriel.interview.exception.BusinessRuleViolationException;
import com.gabriel.interview.exception.ConflictException;
import com.gabriel.interview.exception.DuplicateQuestionException;
import com.gabriel.interview.exception.DuplicateTopicException;
import com.gabriel.interview.exception.InvalidInputException;
import com.gabriel.interview.exception.InvalidQuestionException;
import com.gabriel.interview.exception.InvalidTopicException;
import com.gabriel.interview.exception.QuestionCannotBeDeletedException;
import com.gabriel.interview.exception.QuestionNotFoundException;
import com.gabriel.interview.exception.ResourceNotFoundException;
import com.gabriel.interview.exception.TopicNotFoundException;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleValidationExceptions_shouldReturnBadRequest_withFieldError() {
        // Arrange
        MethodParameter parameter = mock(MethodParameter.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("questionCreateRequest", "questionText", "must not be blank");
        
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);

        // Act
        ErrorResponse response = globalExceptionHandler.handleValidationExceptions(ex);

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatusCode());
        assertEquals("questionText: must not be blank", response.getMessage());
    }

    @Test
    void handleValidationExceptions_shouldReturnBadRequest_withMultipleErrors() {
        // Arrange
        MethodParameter parameter = mock(MethodParameter.class);
        BindingResult bindingResult = mock(BindingResult.class);
        
        FieldError error1 = new FieldError("request", "field1", "error1");
        FieldError error2 = new FieldError("request", "field2", "error2");
        
        when(bindingResult.getFieldErrors()).thenReturn(List.of(error1, error2));
        
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);

        // Act
        ErrorResponse response = globalExceptionHandler.handleValidationExceptions(ex);

        // Assert
        assertEquals(400, response.getStatusCode());
        assertEquals("field1: error1", response.getMessage());
    }

    @Test
    void handleValidationExceptions_shouldReturnDefaultMessage_whenNoFieldErrors() {
        // Arrange
        MethodParameter parameter = mock(MethodParameter.class);
        BindingResult bindingResult = mock(BindingResult.class);
        
        when(bindingResult.getFieldErrors()).thenReturn(List.of());
        
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);

        // Act
        ErrorResponse response = globalExceptionHandler.handleValidationExceptions(ex);

        // Assert
        assertEquals(400, response.getStatusCode());
        assertEquals("Invalid request", response.getMessage());
    }

    @Test
    void handleHttpMessageNotReadable_shouldReturnBadRequest() {
        // Arrange
        byte[] body = "{\"difficulty\":\"VERY HARD\"}".getBytes();
        HttpInputMessage inputMessage = new MockHttpInputMessage(body);
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Not valid difficulty value", inputMessage);

        // Act
        ErrorResponse response = globalExceptionHandler.handleHttpMessageNotReadable(ex);

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatusCode());
        assertNotNull(response.getMessage());
    }

    @Test
    void handleNotFound_shouldReturnNotFound_forQuestionNotFoundException() {
        // Arrange
        QuestionNotFoundException ex = new QuestionNotFoundException(123L);

        // Act
        ErrorResponse response = globalExceptionHandler.handleNotFound(ex);

        // Assert
        assertEquals(404, response.getStatusCode());
        assertEquals("Question with id 123 was not found", response.getMessage());
    }

    @Test
    void handleNotFound_shouldReturnNotFound_forTopicNotFoundException() {
        // Arrange
        TopicNotFoundException ex = new TopicNotFoundException("Topic not found with ID: 456");

        // Act
        ErrorResponse response = globalExceptionHandler.handleNotFound(ex);

        // Assert
        assertEquals(404, response.getStatusCode());
        assertEquals("Topic not found with ID: 456", response.getMessage());
    }

    @Test
    void handleNotFound_shouldReturnNotFound_forResourceNotFoundException() {
        // Arrange
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");

        // Act
        ErrorResponse response = globalExceptionHandler.handleNotFound(ex);

        // Assert
        assertEquals(404, response.getStatusCode());
        assertEquals("Resource not found", response.getMessage());
    }

    @Test
    void handleInvalidInput_shouldReturnBadRequest_forInvalidQuestionException() {
        // Arrange
        InvalidQuestionException ex = new InvalidQuestionException("Question ID must be a positive number");

        // Act
        ErrorResponse response = globalExceptionHandler.handleInvalidInput(ex);

        // Assert
        assertEquals(400, response.getStatusCode());
        assertEquals("Question ID must be a positive number", response.getMessage());
    }

    @Test
    void handleInvalidInput_shouldReturnBadRequest_forInvalidTopicException() {
        // Arrange
        InvalidTopicException ex = new InvalidTopicException("Topic ID must be a positive number");

        // Act
        ErrorResponse response = globalExceptionHandler.handleInvalidInput(ex);

        // Assert
        assertEquals(400, response.getStatusCode());
        assertEquals("Topic ID must be a positive number", response.getMessage());
    }

    @Test
    void handleInvalidInput_shouldReturnBadRequest_forInvalidInputException() {
        // Arrange
        InvalidInputException ex = new InvalidInputException("Invalid input provided");

        // Act
        ErrorResponse response = globalExceptionHandler.handleInvalidInput(ex);

        // Assert
        assertEquals(400, response.getStatusCode());
        assertEquals("Invalid input provided", response.getMessage());
    }

    @Test
    void handleConflict_shouldReturnConflict_forDuplicateQuestionException() {
        // Arrange
        DuplicateQuestionException ex = new DuplicateQuestionException("A question with the same text already exists");

        // Act
        ErrorResponse response = globalExceptionHandler.handleConflict(ex);

        // Assert
        assertEquals(409, response.getStatusCode());
        assertEquals("A question with the same text already exists", response.getMessage());
    }

    @Test
    void handleConflict_shouldReturnConflict_forDuplicateTopicException() {
        // Arrange
        DuplicateTopicException ex = new DuplicateTopicException("A topic with the same name already exists");

        // Act
        ErrorResponse response = globalExceptionHandler.handleConflict(ex);

        // Assert
        assertEquals(409, response.getStatusCode());
        assertEquals("A topic with the same name already exists", response.getMessage());
    }

    @Test
    void handleConflict_shouldReturnConflict_forConflictException() {
        // Arrange
        ConflictException ex = new ConflictException("Conflict detected");

        // Act
        ErrorResponse response = globalExceptionHandler.handleConflict(ex);

        // Assert
        assertEquals(409, response.getStatusCode());
        assertEquals("Conflict detected", response.getMessage());
    }

    @Test
    void handleBusinessRuleViolation_shouldReturnUnprocessableEntity() {
        // Arrange
        QuestionCannotBeDeletedException ex = new QuestionCannotBeDeletedException("Question cannot be deleted because it is referenced");

        // Act
        ErrorResponse response = globalExceptionHandler.handleBusinessRuleViolation(ex);

        // Assert
        assertEquals(422, response.getStatusCode());
        assertEquals("Question cannot be deleted because it is referenced", response.getMessage());
    }

    @Test
    void handleBusinessRuleViolation_shouldReturnUnprocessableEntity_forBusinessRuleViolationException() {
        // Arrange
        BusinessRuleViolationException ex = new BusinessRuleViolationException("Business rule violated");

        // Act
        ErrorResponse response = globalExceptionHandler.handleBusinessRuleViolation(ex);

        // Assert
        assertEquals(422, response.getStatusCode());
        assertEquals("Business rule violated", response.getMessage());
    }

    @Test
    void handleDefaultException_shouldReturnInternalServerError() {
        // Arrange
        Exception ex = new Exception("Unexpected error occurred");

        // Act
        ErrorResponse response = globalExceptionHandler.handleDefaultException(ex);

        // Assert
        assertEquals(500, response.getStatusCode());
        assertEquals("Unexpected error occurred", response.getMessage());
    }

    @Test
    void handleDefaultException_shouldReturnInternalServerError_forRuntimeException() {
        // Arrange
        RuntimeException ex = new RuntimeException("Runtime error");

        // Act
        ErrorResponse response = globalExceptionHandler.handleDefaultException(ex);

        // Assert
        assertEquals(500, response.getStatusCode());
        assertEquals("Runtime error", response.getMessage());
    }

    @Test
    void handleDefaultException_shouldReturnInternalServerError_forNullPointerException() {
        // Arrange
        NullPointerException ex = new NullPointerException("Null pointer");

        // Act
        ErrorResponse response = globalExceptionHandler.handleDefaultException(ex);

        // Assert
        assertEquals(500, response.getStatusCode());
        assertEquals("Null pointer", response.getMessage());
    }

    @Test
    void errorResponse_shouldHaveCorrectHttpStatusValues() {
        // Assert
        assertEquals(400, HttpStatus.BAD_REQUEST.value());
        assertEquals(404, HttpStatus.NOT_FOUND.value());
        assertEquals(409, HttpStatus.CONFLICT.value());
        assertEquals(422, HttpStatus.UNPROCESSABLE_CONTENT.value());
        assertEquals(500, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
