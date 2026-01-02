package com.gabriel.interview.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.interview.dto.QuestionCreateRequest;
import com.gabriel.interview.dto.QuestionResponse;
import com.gabriel.interview.dto.TopicResponse;
import com.gabriel.interview.exception.TopicNotFoundException;
import com.gabriel.interview.model.Difficulty;
import com.gabriel.interview.model.Question;
import com.gabriel.interview.model.StudyArea;
import com.gabriel.interview.model.Topic;
import com.gabriel.interview.service.QuestionService;
import com.gabriel.interview.service.TopicService;

@SpringBootTest
public class QuestionControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper jacksonMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private QuestionService questionService;

    @MockitoBean
    private TopicService topicService;

    @MockitoBean
    private com.gabriel.interview.mapper.ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAllQuestions_shouldReturnListOfQuestions() throws Exception {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming language");
        studyArea.setId(1L);
        
        Topic topic = new Topic("Core Java", studyArea);
        topic.setId(1L);

        Question question = new Question("What is Java?", "Java is a programming language", Difficulty.EASY, topic);
        question.setId(1L);

        TopicResponse topicResponse = new TopicResponse(1L, "Core Java");
        QuestionResponse questionResponse = new QuestionResponse(
            1L, 
            "What is Java?", 
            "Java is a programming language", 
            "EASY", 
            topicResponse
        );

        when(questionService.getAllQuestions()).thenReturn(List.of(question));
        when(objectMapper.toQuestionResponse(any(Question.class))).thenReturn(questionResponse);

        // Act & Assert
        mockMvc.perform(get("/questions/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].questionText").value("What is Java?"))
                .andExpect(jsonPath("$[0].answer").value("Java is a programming language"))
                .andExpect(jsonPath("$[0].difficulty").value("EASY"))
                .andExpect(jsonPath("$[0].topic.id").value(1))
                .andExpect(jsonPath("$[0].topic.name").value("Core Java"));
    }

    @Test
    void getAllQuestions_shouldReturnEmptyList_whenNoQuestionsExist() throws Exception {
        // Arrange
        when(questionService.getAllQuestions()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/questions/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createQuestion_shouldReturnCreatedQuestion_whenValidInput() throws Exception {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming language");
        studyArea.setId(1L);
        
        Topic topic = new Topic("Core Java", studyArea);
        topic.setId(1L);

        QuestionCreateRequest request = new QuestionCreateRequest();
        request.setQuestionText("What is polymorphism?");
        request.setAnswer("Polymorphism is the ability of objects to take many forms");
        request.setDifficulty(Difficulty.MEDIUM);
        request.setTopicId(1L);

        Question newQuestion = new Question(
            "What is polymorphism?",
            "Polymorphism is the ability of objects to take many forms",
            Difficulty.MEDIUM,
            topic
        );

        Question createdQuestion = new Question(
            "What is polymorphism?",
            "Polymorphism is the ability of objects to take many forms",
            Difficulty.MEDIUM,
            topic
        );
        createdQuestion.setId(2L);

        TopicResponse topicResponse = new TopicResponse(1L, "Core Java");
        QuestionResponse questionResponse = new QuestionResponse(
            2L,
            "What is polymorphism?",
            "Polymorphism is the ability of objects to take many forms",
            "MEDIUM",
            topicResponse
        );

        when(topicService.getTopicById(1L)).thenReturn(topic);
        when(objectMapper.toQuestionEntity(any(QuestionCreateRequest.class), eq(topic))).thenReturn(newQuestion);
        when(questionService.createQuestion(any(Question.class))).thenReturn(createdQuestion);
        when(objectMapper.toQuestionResponse(any(Question.class))).thenReturn(questionResponse);

        // Act & Assert
        mockMvc.perform(post("/questions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.questionText").value("What is polymorphism?"))
                .andExpect(jsonPath("$.difficulty").value("MEDIUM"));
    }

    @Test
    void createQuestion_shouldReturn404_whenTopicNotFound() throws Exception {
        // Arrange
        QuestionCreateRequest request = new QuestionCreateRequest();
        request.setQuestionText("What is polymorphism?");
        request.setAnswer("Polymorphism is the ability of objects to take many forms");
        request.setDifficulty(Difficulty.MEDIUM);
        request.setTopicId(999L);

        when(topicService.getTopicById(999L)).thenThrow(new TopicNotFoundException("Topic not found with ID: 999"));

        // Act & Assert
        mockMvc.perform(post("/questions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Topic not found with ID: 999"));
    }

    @Test
    void createQuestion_shouldReturn400_whenQuestionTextIsBlank() throws Exception {
        // Arrange
        QuestionCreateRequest request = new QuestionCreateRequest();
        request.setQuestionText("");
        request.setAnswer("Some answer");
        request.setDifficulty(Difficulty.EASY);
        request.setTopicId(1L);

        // Act & Assert
        mockMvc.perform(post("/questions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    void createQuestion_shouldReturn400_whenAnswerIsBlank() throws Exception {
        // Arrange
        QuestionCreateRequest request = new QuestionCreateRequest();
        request.setQuestionText("What is Java?");
        request.setAnswer("");
        request.setDifficulty(Difficulty.EASY);
        request.setTopicId(1L);

        // Act & Assert
        mockMvc.perform(post("/questions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    void createQuestion_shouldReturn400_whenDifficultyIsNull() throws Exception {
        // Arrange
        QuestionCreateRequest request = new QuestionCreateRequest();
        request.setQuestionText("What is Java?");
        request.setAnswer("Java is a programming language");
        request.setDifficulty(null);
        request.setTopicId(1L);

        // Act & Assert
        mockMvc.perform(post("/questions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    void createQuestion_shouldReturn400_whenTopicIdIsNull() throws Exception {
        // Arrange
        QuestionCreateRequest request = new QuestionCreateRequest();
        request.setQuestionText("What is Java?");
        request.setAnswer("Java is a programming language");
        request.setDifficulty(Difficulty.EASY);
        request.setTopicId(null);

        // Act & Assert
        mockMvc.perform(post("/questions/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400));
    }
}
