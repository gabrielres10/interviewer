package com.gabriel.interview.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gabriel.interview.dto.QuestionCreateRequest;
import com.gabriel.interview.dto.QuestionResponse;
import com.gabriel.interview.dto.TopicResponse;
import com.gabriel.interview.model.Difficulty;
import com.gabriel.interview.model.Question;
import com.gabriel.interview.model.StudyArea;
import com.gabriel.interview.model.Topic;

public class ObjectMapperTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void toQuestionEntity_shouldMapDtoToEntity() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming language");
        studyArea.setId(1L);
        
        Topic topic = new Topic("Core Java", studyArea);
        topic.setId(1L);

        QuestionCreateRequest dto = new QuestionCreateRequest();
        dto.setQuestionText("What is polymorphism?");
        dto.setAnswer("Polymorphism is the ability of objects to take many forms");
        dto.setDifficulty(Difficulty.MEDIUM);
        dto.setTopicId(1L);

        // Act
        Question result = objectMapper.toQuestionEntity(dto, topic);

        // Assert
        assertNotNull(result, "Mapped Question entity should not be null");
        assertEquals("What is polymorphism?", result.getQuestionText(), "Question text should be correctly mapped from DTO");
        assertEquals("Polymorphism is the ability of objects to take many forms", result.getAnswer(), "Answer should be correctly mapped from DTO");
        assertEquals(Difficulty.MEDIUM, result.getDifficulty(), "Difficulty should be MEDIUM as specified in DTO");
        assertEquals(topic, result.getTopic(), "Topic should be the provided topic entity");
        assertEquals("Core Java", result.getTopic().getName(), "Topic name should be 'Core Java'");
    }

    @Test
    void toQuestionEntity_shouldMapWithEasyDifficulty() {
        // Arrange
        StudyArea studyArea = new StudyArea("Python", "Python programming");
        Topic topic = new Topic("Python Basics", studyArea);

        QuestionCreateRequest dto = new QuestionCreateRequest();
        dto.setQuestionText("What is Python?");
        dto.setAnswer("Python is a high-level programming language");
        dto.setDifficulty(Difficulty.EASY);
        dto.setTopicId(1L);

        // Act
        Question result = objectMapper.toQuestionEntity(dto, topic);

        // Assert
        assertEquals(Difficulty.EASY, result.getDifficulty(), "Difficulty should be EASY as specified in DTO");
    }

    @Test
    void toQuestionEntity_shouldMapWithHardDifficulty() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming");
        Topic topic = new Topic("Advanced Java", studyArea);

        QuestionCreateRequest dto = new QuestionCreateRequest();
        dto.setQuestionText("Explain metaclasses in Python");
        dto.setAnswer("Metaclasses are classes of classes that define how classes behave");
        dto.setDifficulty(Difficulty.HARD);
        dto.setTopicId(1L);

        // Act
        Question result = objectMapper.toQuestionEntity(dto, topic);

        // Assert
        assertEquals(Difficulty.HARD, result.getDifficulty(), "Difficulty should be HARD as specified in DTO");
    }

    @Test
    void toQuestionResponse_shouldMapEntityToDto() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming language");
        studyArea.setId(1L);
        
        Topic topic = new Topic("Core Java", studyArea);
        topic.setId(1L);

        Question question = new Question(
            "What is encapsulation?",
            "Encapsulation is the bundling of data with methods that operate on that data",
            Difficulty.MEDIUM,
            topic
        );
        question.setId(5L);

        // Act
        QuestionResponse result = objectMapper.toQuestionResponse(question);

        // Assert
        assertNotNull(result, "Mapped QuestionResponse DTO should not be null");
        assertEquals(5L, result.getId(), "Question ID should be correctly mapped to response");
        assertEquals("What is encapsulation?", result.getQuestionText(), "Question text should be correctly mapped to response");
        assertEquals("Encapsulation is the bundling of data with methods that operate on that data", result.getAnswer(), "Answer should be correctly mapped to response");
        assertEquals("MEDIUM", result.getDifficulty(), "Difficulty should be mapped as string 'MEDIUM'");
        assertNotNull(result.getTopic(), "Topic in QuestionResponse should not be null");
        assertEquals(1L, result.getTopic().getId(), "Topic ID should be correctly mapped");
        assertEquals("Core Java", result.getTopic().getName(), "Topic name should be 'Core Java'");
    }

    @Test
    void toQuestionResponse_shouldMapDifficultyAsString() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming");
        Topic topic = new Topic("OOP", studyArea);
        topic.setId(2L);

        Question easyQuestion = new Question("What is a class?", "A class is a blueprint", Difficulty.EASY, topic);
        Question mediumQuestion = new Question("What is inheritance?", "Inheritance is...", Difficulty.MEDIUM, topic);
        Question hardQuestion = new Question("What is reflection?", "Reflection is...", Difficulty.HARD, topic);

        // Act
        QuestionResponse easyResponse = objectMapper.toQuestionResponse(easyQuestion);
        QuestionResponse mediumResponse = objectMapper.toQuestionResponse(mediumQuestion);
        QuestionResponse hardResponse = objectMapper.toQuestionResponse(hardQuestion);

        // Assert
        assertEquals("EASY", easyResponse.getDifficulty(), "EASY difficulty should be mapped as string 'EASY'");
        assertEquals("MEDIUM", mediumResponse.getDifficulty(), "MEDIUM difficulty should be mapped as string 'MEDIUM'");
        assertEquals("HARD", hardResponse.getDifficulty(), "HARD difficulty should be mapped as string 'HARD'");
    }

    @Test
    void toTopicResponse_shouldMapTopicToDto() {
        // Arrange
        StudyArea studyArea = new StudyArea("JavaScript", "JavaScript programming");
        Topic topic = new Topic("ES6 Features", studyArea);
        topic.setId(10L);

        // Act
        TopicResponse result = objectMapper.toTopicResponse(topic);

        // Assert
        assertNotNull(result, "Mapped TopicResponse should not be null");
        assertEquals(10L, result.getId(), "Topic ID should be correctly mapped");
        assertEquals("ES6 Features", result.getName(), "Topic name should be 'ES6 Features'");
    }

    @Test
    void toTopicResponse_shouldHandleNullId() {
        // Arrange
        StudyArea studyArea = new StudyArea("Python", "Python programming");
        Topic topic = new Topic("Django", studyArea);
        // ID is null (not set)

        // Act
        TopicResponse result = objectMapper.toTopicResponse(topic);

        // Assert
        assertNotNull(result, "Mapped TopicResponse should not be null even with null ID");
        assertEquals(null, result.getId(), "Topic ID should be null when not set");
        assertEquals("Django", result.getName(), "Topic name should be 'Django'");
    }

    @Test
    void toQuestionResponse_shouldIncludeAllTopicInformation() {
        // Arrange
        StudyArea studyArea = new StudyArea("C++", "C++ programming");
        studyArea.setId(3L);
        
        Topic topic = new Topic("STL", studyArea);
        topic.setId(15L);

        Question question = new Question(
            "What is std::vector?",
            "std::vector is a dynamic array",
            Difficulty.MEDIUM,
            topic
        );
        question.setId(100L);

        // Act
        QuestionResponse result = objectMapper.toQuestionResponse(question);

        // Assert
        assertEquals(100L, result.getId(), "Question ID should be 100");
        assertEquals(15L, result.getTopic().getId(), "Topic ID should be 15");
        assertEquals("STL", result.getTopic().getName(), "Topic name should be 'STL'");
    }

    @Test
    void toQuestionEntity_shouldPreserveAllFields() {
        // Arrange
        StudyArea studyArea = new StudyArea("Database", "Database concepts");
        Topic topic = new Topic("SQL", studyArea);

        String longQuestionText = "Explain the differences between INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL OUTER JOIN in SQL";
        String longAnswer = "INNER JOIN returns records that have matching values in both tables. " +
                           "LEFT JOIN returns all records from the left table and matched records from the right table. " +
                           "RIGHT JOIN returns all records from the right table and matched records from the left table. " +
                           "FULL OUTER JOIN returns all records when there is a match in either left or right table.";

        QuestionCreateRequest dto = new QuestionCreateRequest();
        dto.setQuestionText(longQuestionText);
        dto.setAnswer(longAnswer);
        dto.setDifficulty(Difficulty.HARD);
        dto.setTopicId(1L);

        // Act
        Question result = objectMapper.toQuestionEntity(dto, topic);

        // Assert
        assertEquals(longQuestionText, result.getQuestionText(), "Long question text should be preserved exactly");
        assertEquals(longAnswer, result.getAnswer(), "Long answer should be preserved exactly");
        assertEquals(Difficulty.HARD, result.getDifficulty(), "Difficulty should be HARD");
    }
}
