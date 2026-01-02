package com.gabriel.interview.dto;

/**
 * Data Transfer Object representing a question response.
 * Used to return question data to clients.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class QuestionResponse {
    private Long id;
    private String questionText;
    private String answer;
    private String difficulty;
    private TopicResponse topic;

    public QuestionResponse(Long id, String questionText, String answer, String difficulty, TopicResponse topic) {
        this.id = id;
        this.questionText = questionText;
        this.answer = answer;
        this.difficulty = difficulty;
        this.topic = topic;
    }

    public QuestionResponse() {
    }

    public Long getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public TopicResponse getTopic() {
        return topic;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setTopic(TopicResponse topic) {
        this.topic = topic;
    }
}
