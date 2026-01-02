package com.gabriel.interview.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gabriel.interview.exception.DuplicateTopicException;
import com.gabriel.interview.exception.InvalidTopicException;
import com.gabriel.interview.exception.TopicNotFoundException;
import com.gabriel.interview.model.Topic;
import com.gabriel.interview.repository.TopicRepository;

/**
 * Service class for managing topics.
 * Handles business logic for topic operations including creation, retrieval, and updates.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
@Service
public class TopicService {
    
    private final TopicRepository topicRepository;

    /**
     * Constructor for TopicService with dependency injection.
     * 
     * @param topicRepository Repository for topic database operations
     */
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Creates a new topic in the database.
     * Validates that a topic with the same name does not already exist.
     * 
     * @param topic The topic entity to create
     * @return The created topic with generated ID
     * @throws DuplicateTopicException if a topic with the same name already exists
     */
    public Topic createTopic(Topic topic) {
        // Verificar si ya existe un tema con el mismo nombre
        if (topicRepository.existsByName(topic.getName())) {
            throw new DuplicateTopicException("A topic with the same name already exists");
        }

        return topicRepository.save(topic);
    }

    /**
     * Retrieves a topic by its ID.
     * 
     * @param id The ID of the topic to retrieve
     * @return The topic entity
     * @throws InvalidTopicException if the ID is null or non-positive
     * @throws TopicNotFoundException if no topic exists with the given ID
     */
    public Topic getTopicById(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidTopicException("Topic ID must be a positive number");
        }

        return topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found with ID: " + id));
    }

    /**
     * Retrieves all topics from the database.
     * 
     * @return List of all topic entities
     */
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    /**
     * Updates an existing topic's name.
     * Validates that the new name does not duplicate an existing topic.
     * 
     * @param id The ID of the topic to update
     * @param updatedTopic The topic entity with updated information
     * @return The updated topic entity
     * @throws InvalidTopicException if the ID is null or non-positive
     * @throws TopicNotFoundException if no topic exists with the given ID
     * @throws DuplicateTopicException if the updated name matches an existing topic
     */
    public Topic updateTopic(Long id, Topic updatedTopic) {
        if( id == null || id <= 0) {
            throw new InvalidTopicException("Topic ID must be a positive number");
        }

        Topic existingTopic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found with ID: " + id));

        // Verificar duplicados solo si el nombre cambió
        if (!existingTopic.getName().equals(updatedTopic.getName())) {
            if (topicRepository.existsByName(updatedTopic.getName())) {
                throw new DuplicateTopicException("A topic with the same name already exists");
            }
        }

        existingTopic.setName(updatedTopic.getName());
        return topicRepository.save(existingTopic);
    }
}
