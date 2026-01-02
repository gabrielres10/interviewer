package com.gabriel.interview.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Size;

/**
 * Entity representing a study topic.
 * Topics belong to study areas and contain multiple questions.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @Size(min = 1, max = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_area_id", nullable = false)
    private StudyArea studyArea;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private List<Question> questions;

    /**
     * Constructor
     * @param name
     * @param studyArea
     */
    public Topic(String name, StudyArea studyArea) {
        this.name = name;
        this.studyArea = studyArea;
    }

    /**
     * Default constructor
     */
    public Topic() {
    }

    /**
     * Get the id of the topic
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Get the name of the topic
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the study area of the topic
     * @return studyArea
     */
    public StudyArea getStudyArea() {
        return this.studyArea;
    }

    /**
     * Set the name of the topic
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the study area of the topic
     * @param studyArea
     */
    public void setStudyArea(StudyArea studyArea) {
        this.studyArea = studyArea;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the questions of the topic
     * @return questions
     */
    public List<Question> getQuestions() {
        return this.questions;
    }    
}    