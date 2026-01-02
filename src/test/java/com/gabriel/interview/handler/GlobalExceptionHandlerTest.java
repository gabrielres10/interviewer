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
        assertNotNull(response, "ErrorResponse should not be null");
        assertEquals(400, response.getStatusCode(), "Status code should be 400 BAD_REQUEST");
        assertEquals("questionText: must not be blank", response.getMessage(), "Error message should contain field validation error");
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
        assertEquals(400, response.getStatusCode(), "Status code should be 400 BAD_REQUEST for multiple validation errors");
        assertEquals("field1: error1", response.getMessage(), "Error message should contain first field error");
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
        assertEquals(400, response.getStatusCode(), "Status code should be 400 BAD_REQUEST when no field errors");
        assertEquals("Invalid request", response.getMessage(), "Should return default message when no field errors");
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
        assertNotNull(response, "ErrorResponse should not be null for HttpMessageNotReadableException");
        assertEquals(400, response.getStatusCode(), "Status code should be 400 BAD_REQUEST for unreadable message");
        assertNotNull(response.getMessage(), "Error message should not be null");
    }

    @Test
    void handleNotFound_shouldReturnNotFound_forQuestionNotFoundException() {
        // Arrange
        QuestionNotFoundException ex = new QuestionNotFoundException(123L);

        // Act
        ErrorResponse response = globalExceptionHandler.handleNotFound(ex);

        // Assert
        assertEquals(404, response.getStatusCode(), "Status code should be 404 NOT_FOUND for QuestionNotFoundException");
        assertEquals("Question with id 123 was not found", response.getMessage(), "Error message should indicate question not found with specific ID");
    }

    @Test
    void handleNotFound_shouldReturnNotFound_forTopicNotFoundException() {
        // Arrange
        TopicNotFoundException ex = new TopicNotFoundException("Topic not found with ID: 456");

        // Act
        ErrorResponse response = globalExceptionHandler.handleNotFound(ex);

        // Assert
        assertEquals(404, response.getStatusCode(), "Status code should be 404 NOT_FOUND for TopicNotFoundException");
        assertEquals("Topic not found with ID: 456", response.getMessage(), "Error message should indicate topic not found with specific ID");
    }

    @Test
    void handleNotFound_shouldReturnNotFound_forResourceNotFoundException() {
        // Arrange
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");

        // Act
        ErrorResponse response = globalExceptionHandler.handleNotFound(ex);

        // Assert
        assertEquals(404, response.getStatusCode(), "Status code should be 404 NOT_FOUND for ResourceNotFoundException");
        assertEquals("Resource not found", response.getMessage(), "Error message should indicate resource not found");
    }

    @Test
    void handleInvalidInput_shouldReturnBadRequest_forInvalidQuestionException() {
        // Arrange
        InvalidQuestionException ex = new InvalidQuestionException("Question ID must be a positive number");

        // Act
        ErrorResponse response = globalExceptionHandler.handleInvalidInput(ex);

        // Assert
        assertEquals(400, response.getStatusCode(), "Status code should be 400 BAD_REQUEST for InvalidQuestionException");
        assertEquals("Question ID must be a positive number", response.getMessage(), "Error message should indicate invalid question ID");
    }

    @Test
    void handleInvalidInput_shouldReturnBadRequest_forInvalidTopicException() {
        // Arrange
        InvalidTopicException ex = new InvalidTopicException("Topic ID must be a positive number");

        // Act
        ErrorResponse response = globalExceptionHandler.handleInvalidInput(ex);

        // Assert
        assertEquals(400, response.getStatusCode(), "Status code should be 400 BAD_REQUEST for InvalidTopicException");
        assertEquals("Topic ID must be a positive number", response.getMessage(), "Error message should indicate invalid topic ID");
    }

    @Test
    void handleInvalidInput_shouldReturnBadRequest_forInvalidInputException() {
        // Arrange
        InvalidInputException ex = new InvalidInputException("Invalid input provided");

        // Act
        ErrorResponse response = globalExceptionHandler.handleInvalidInput(ex);

        // Assert
        assertEquals(400, response.getStatusCode(), "Status code should be 400 BAD_REQUEST for InvalidInputException");
        assertEquals("Invalid input provided", response.getMessage(), "Error message should indicate invalid input");
    }

    @Test
    void handleConflict_shouldReturnConflict_forDuplicateQuestionException() {
        // Arrange
        DuplicateQuestionException ex = new DuplicateQuestionException("A question with the same text already exists");

        // Act
        ErrorResponse response = globalExceptionHandler.handleConflict(ex);

        // Assert
        assertEquals(409, response.getStatusCode(), "Status code should be 409 CONFLICT for DuplicateQuestionException");
        assertEquals("A question with the same text already exists", response.getMessage(), "Error message should indicate duplicate question");
    }

    @Test
    void handleConflict_shouldReturnConflict_forDuplicateTopicException() {
        // Arrange
        DuplicateTopicException ex = new DuplicateTopicException("A topic with the same name already exists");

        // Act
        ErrorResponse response = globalExceptionHandler.handleConflict(ex);

        // Assert
        assertEquals(409, response.getStatusCode(), "Status code should be 409 CONFLICT for DuplicateTopicException");
        assertEquals("A topic with the same name already exists", response.getMessage(), "Error message should indicate duplicate topic");
    }

    @Test
    void handleConflict_shouldReturnConflict_forConflictException() {
        // Arrange
        ConflictException ex = new ConflictException("Conflict detected");

        // Act
        ErrorResponse response = globalExceptionHandler.handleConflict(ex);

        // Assert
        assertEquals(409, response.getStatusCode(), "Status code should be 409 CONFLICT for ConflictException");
        assertEquals("Conflict detected", response.getMessage(), "Error message should indicate conflict detected");
    }

    @Test
    void handleBusinessRuleViolation_shouldReturnUnprocessableEntity() {
        // Arrange
        QuestionCannotBeDeletedException ex = new QuestionCannotBeDeletedException("Question cannot be deleted because it is referenced");

        // Act
        ErrorResponse response = globalExceptionHandler.handleBusinessRuleViolation(ex);

        // Assert
        assertEquals(422, response.getStatusCode(), "Status code should be 422 UNPROCESSABLE_CONTENT for QuestionCannotBeDeletedException");
        assertEquals("Question cannot be deleted because it is referenced", response.getMessage(), "Error message should indicate question cannot be deleted");
    }

    @Test
    void handleBusinessRuleViolation_shouldReturnUnprocessableEntity_forBusinessRuleViolationException() {
        // Arrange
        BusinessRuleViolationException ex = new BusinessRuleViolationException("Business rule violated");

        // Act
        ErrorResponse response = globalExceptionHandler.handleBusinessRuleViolation(ex);

        // Assert
        assertEquals(422, response.getStatusCode(), "Status code should be 422 UNPROCESSABLE_CONTENT for BusinessRuleViolationException");
        assertEquals("Business rule violated", response.getMessage(), "Error message should indicate business rule violation");
    }

    @Test
    void handleDefaultException_shouldReturnInternalServerError() {
        // Arrange
        Exception ex = new Exception("Unexpected error occurred");

        // Act
        ErrorResponse response = globalExceptionHandler.handleDefaultException(ex);

        // Assert
        assertEquals(500, response.getStatusCode(), "Status code should be 500 INTERNAL_SERVER_ERROR for generic Exception");
        assertEquals("Unexpected error occurred", response.getMessage(), "Error message should indicate unexpected error");
    }

    @Test
    void handleDefaultException_shouldReturnInternalServerError_forRuntimeException() {
        // Arrange
        RuntimeException ex = new RuntimeException("Runtime error");

        // Act
        ErrorResponse response = globalExceptionHandler.handleDefaultException(ex);

        // Assert
        assertEquals(500, response.getStatusCode(), "Status code should be 500 INTERNAL_SERVER_ERROR for RuntimeException");
        assertEquals("Runtime error", response.getMessage(), "Error message should indicate runtime error");
    }

    @Test
    void handleDefaultException_shouldReturnInternalServerError_forNullPointerException() {
        // Arrange
        NullPointerException ex = new NullPointerException("Null pointer");

        // Act
        ErrorResponse response = globalExceptionHandler.handleDefaultException(ex);

        // Assert
        assertEquals(500, response.getStatusCode(), "Status code should be 500 INTERNAL_SERVER_ERROR for NullPointerException");
        assertEquals("Null pointer", response.getMessage(), "Error message should indicate null pointer");
    }

    @Test
    void errorResponse_shouldHaveCorrectHttpStatusValues() {
        // Assert
        assertEquals(400, HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST should have value 400");
        assertEquals(404, HttpStatus.NOT_FOUND.value(), "NOT_FOUND should have value 404");
        assertEquals(409, HttpStatus.CONFLICT.value(), "CONFLICT should have value 409");
        assertEquals(422, HttpStatus.UNPROCESSABLE_CONTENT.value(), "UNPROCESSABLE_CONTENT should have value 422");
        assertEquals(500, HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR should have value 500");
    }
}
