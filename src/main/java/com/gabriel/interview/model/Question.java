package com.gabriel.interview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Entity representing an interview question.
 * Each question belongs to a topic and has a difficulty level.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
@Entity
public class Question {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "question_text", nullable = false, length = 2000)
    @Size(max = 2000)
    @NotBlank(message = "Question text must not be blank")
    private String questionText;
    
    @Column(name = "answer", nullable = false, length = 4000)
    @Size(max = 4000)
    @NotBlank(message = "Answer must not be blank")
    private String answer;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Difficulty must not be null")
    private Difficulty difficulty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    @NotNull(message = "Topic must not be null")
    private Topic topic;

    /**
     * Constructor
     * @param questionText
     * @param answer
     * @param difficulty
     * @param topic
     */
    public Question(String questionText, String answer, Difficulty difficulty, Topic topic) {
        this.questionText = questionText;
        this.answer = answer;
        this.difficulty = difficulty;
        this.topic = topic;
    }

    /**
     * Default constructor
     */
    public Question() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the question text
     * @return questionText
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Set the question text
     * @param questionText the question text
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * Get the answer
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Set the answer
     * @param answer the answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Get the difficulty
     * @return difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Set the difficulty
     * @param difficulty the difficulty level
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Get the topic
     * @return topic
     */
    public Topic getTopic() {
        return topic;
    }

    /**
     * Set the topic
     * @param topic the topic
     */
    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    /**
     * Get the ID
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * String representation of the Question
     * @return String
     */
    @Override
    public String toString() {
        return "Question{" +
                "questionText='" + questionText + '\'' +
                ", answer='" + answer + '\'' +
                ", difficulty=" + difficulty +
                ", topic=" + topic.getName() +
                '}';
    }
}
