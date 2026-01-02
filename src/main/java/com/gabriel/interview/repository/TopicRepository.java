package com.gabriel.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gabriel.interview.model.Topic;

/**
 * Repository interface for Topic entities.
 * Provides data access methods for topic operations.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public interface TopicRepository extends JpaRepository<Topic, Long>{
    /**
     * Checks if a topic with the specified name exists.
     * 
     * @param name The name of the topic to check
     * @return true if a topic with the name exists, false otherwise
     */
    boolean existsByName(String name);
}
