package com.gabriel.interview.dto;

import com.gabriel.interview.model.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating a new question.
 * Contains validation constraints for question creation.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class QuestionCreateRequest {
    @NotBlank(message = "Question text must not be blank")
    @Size(max = 2000, message = "Question text must not exceed 2000 characters")
    private String questionText;

    @NotBlank(message = "Answer must not be blank")
    @Size(max = 4000, message = "Answer must not exceed 4000 characters")
    private String answer;

    @NotNull(message = "Difficulty must not be null")
    private Difficulty difficulty;

    @NotNull(message = "Topic ID must not be null")
    private Long topicId;

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    @Override
    public String toString() {
        return "QuestionCreateRequest{" +
                "questionText='" + questionText + '\'' +
                ", answer='" + answer + '\'' +
                ", difficulty=" + difficulty +
                ", topicId=" + topicId +
                '}';
    }
}
