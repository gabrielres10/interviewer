package com.gabriel.interview.controller;

import org.springframework.web.bind.annotation.RestController;

import com.gabriel.interview.dto.QuestionCreateRequest;
import com.gabriel.interview.dto.QuestionResponse;
import com.gabriel.interview.mapper.ObjectMapper;
import com.gabriel.interview.model.Question;
import com.gabriel.interview.model.Topic;
import com.gabriel.interview.service.QuestionService;
import com.gabriel.interview.service.TopicService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * REST Controller for managing interview questions.
 * Provides endpoints for creating and retrieving questions.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
@RestController
@RequestMapping("/questions")
@Tag(name = "Questions", description = "API for interview questions management")
public class QuestionController {
    
    private final QuestionService questionService;
    private final TopicService topicService;
    private final ObjectMapper objectMapper;

    /**
     * Constructor for QuestionController with dependency injection.
     * 
     * @param questionService Service for question operations
     * @param topicService Service for topic operations
     * @param objectMapper Mapper for converting between entities and DTOs
     */
    public QuestionController(QuestionService questionService, TopicService topicService, ObjectMapper objectMapper) {
        this.questionService = questionService;
        this.topicService = topicService;
        this.objectMapper = objectMapper;
    }
    
    /**
     * Retrieves all available interview questions.
     * 
     * @return ResponseEntity containing a list of QuestionResponse objects
     */
    @GetMapping("/all")
    @Operation(summary = "Get all questions", description = "Retrieves a list of all available interview questions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of questions",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Question.class)))
    })
    public ResponseEntity<List<QuestionResponse>> getAllQuestions() {
        List<QuestionResponse> questions = questionService.getAllQuestions()
                .stream()
                .map(objectMapper::toQuestionResponse)
                .toList();
        return ResponseEntity.ok(questions);
    }

    /**
     * Creates a new interview question.
     * 
     * @param question The question creation request containing question details
     * @return ResponseEntity containing the created QuestionResponse
     */
    @PostMapping("/create")
    @Operation(summary = "Create a new question", description = "Creates a new interview question associated with a topic")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Question successfully created",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "404", description = "Topic not found", content = @Content)
    })
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody @Valid QuestionCreateRequest question) {
        Topic relatedTopic = topicService.getTopicById(question.getTopicId());
        Question newQuestion = objectMapper.toQuestionEntity(question, relatedTopic);
        
        Question createdQuestion = questionService.createQuestion(newQuestion);

        QuestionResponse response = objectMapper.toQuestionResponse(createdQuestion);
        return ResponseEntity.ok(response);
    }

}
