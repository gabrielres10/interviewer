package com.gabriel.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gabriel.interview.model.StudyArea;

/**
 * Repository interface for StudyArea entities.
 * Provides data access methods for study area operations.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public interface StudyAreaRepository extends JpaRepository<StudyArea, Long> {
    
}
