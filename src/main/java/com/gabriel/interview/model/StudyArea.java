package com.gabriel.interview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

/**
 * Entity representing a study area.
 * Study areas contain multiple topics for organizing interview questions.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
@Entity
public class StudyArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @Size(max = 100)
    @NotBlank(message = "Name must not be blank")
    private String name;

    @Column(name = "description", nullable = false, length = 500)
    @Size(max = 500)
    @NotBlank(message = "Description must not be blank")
    private String description;

    /**
     * Constructor
     * @param name
     * @param description
     */
    public StudyArea(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Default constructor
     */
    public StudyArea() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the study area
     * @return name
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the study area
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
