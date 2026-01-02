package com.gabriel.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gabriel.interview.model.Question;

import java.util.Optional;

/**
 * Repository interface for Question entities.
 * Provides data access methods for question operations.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public interface QuestionRepository extends JpaRepository<Question, Long>{
    
    /**
     * Checks if a question with the specified text exists.
     * 
     * @param questionText The text of the question to check
     * @return true if a question with the text exists, false otherwise
     */
    boolean existsByQuestionText(String questionText);
    
    /**
     * Finds a question by its text.
     * 
     * @param questionText The text of the question to find
     * @return Optional containing the question if found, empty otherwise
     */
    Optional<Question> findByQuestionText(String questionText);
}
