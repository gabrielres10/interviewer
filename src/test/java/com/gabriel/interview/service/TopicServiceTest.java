package com.gabriel.interview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gabriel.interview.exception.DuplicateTopicException;
import com.gabriel.interview.exception.InvalidTopicException;
import com.gabriel.interview.exception.TopicNotFoundException;
import com.gabriel.interview.model.StudyArea;
import com.gabriel.interview.model.Topic;
import com.gabriel.interview.repository.TopicRepository;

@ExtendWith(MockitoExtension.class)
public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    @Test
    void createTopic_shouldSaveTopic_whenNoDuplicateExists() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming");
        studyArea.setId(1L);
        
        Topic topic = new Topic("Core Java", studyArea);

        when(topicRepository.existsByName("Core Java")).thenReturn(false);
        when(topicRepository.save(topic)).thenReturn(topic);

        // Act
        Topic result = topicService.createTopic(topic);

        // Assert
        assertNotNull(result);
        verify(topicRepository).save(topic);
    }

    @Test
    void createTopic_shouldThrowException_whenDuplicateExists() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming");
        Topic topic = new Topic("Core Java", studyArea);

        when(topicRepository.existsByName("Core Java")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateTopicException.class, () -> topicService.createTopic(topic));
        verify(topicRepository, never()).save(any());
    }

    @Test
    void getTopicById_shouldReturnTopic_whenExists() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming");
        Topic topic = new Topic("Core Java", studyArea);
        topic.setId(1L);

        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));

        // Act
        Topic result = topicService.getTopicById(1L);

        // Assert
        assertEquals("Core Java", result.getName());
        assertEquals(1L, result.getId());
    }

    @Test
    void getTopicById_shouldThrowException_whenIdIsNull() {
        // Act & Assert
        assertThrows(InvalidTopicException.class, () -> topicService.getTopicById(null));
    }

    @Test
    void getTopicById_shouldThrowException_whenIdIsZero() {
        // Act & Assert
        assertThrows(InvalidTopicException.class, () -> topicService.getTopicById(0L));
    }

    @Test
    void getTopicById_shouldThrowException_whenIdIsNegative() {
        // Act & Assert
        assertThrows(InvalidTopicException.class, () -> topicService.getTopicById(-1L));
    }

    @Test
    void getTopicById_shouldThrowException_whenTopicNotFound() {
        // Arrange
        when(topicRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TopicNotFoundException.class, () -> topicService.getTopicById(999L));
    }

    @Test
    void getAllTopics_shouldReturnListOfTopics() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming");
        Topic topic1 = new Topic("Core Java", studyArea);
        Topic topic2 = new Topic("Advanced Java", studyArea);

        when(topicRepository.findAll()).thenReturn(List.of(topic1, topic2));

        // Act
        List<Topic> result = topicService.getAllTopics();

        // Assert
        assertEquals(2, result.size());
        verify(topicRepository).findAll();
    }

    @Test
    void getAllTopics_shouldReturnEmptyList_whenNoTopicsExist() {
        // Arrange
        when(topicRepository.findAll()).thenReturn(List.of());

        // Act
        List<Topic> result = topicService.getAllTopics();

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    void updateTopic_shouldUpdateTopic_whenValid() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming");
        Topic existing = new Topic("Old Name", studyArea);
        existing.setId(1L);

        Topic updated = new Topic("New Name", studyArea);

        when(topicRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(topicRepository.existsByName("New Name")).thenReturn(false);
        when(topicRepository.save(existing)).thenReturn(existing);

        // Act
        Topic result = topicService.updateTopic(1L, updated);

        // Assert
        assertEquals("New Name", result.getName());
        verify(topicRepository).save(existing);
    }

    @Test
    void updateTopic_shouldNotCheckDuplicate_whenNameUnchanged() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming");
        Topic existing = new Topic("Same Name", studyArea);
        existing.setId(1L);

        Topic updated = new Topic("Same Name", studyArea);

        when(topicRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(topicRepository.save(existing)).thenReturn(existing);

        // Act
        Topic result = topicService.updateTopic(1L, updated);

        // Assert
        assertEquals("Same Name", result.getName());
        verify(topicRepository, never()).existsByName(any());
        verify(topicRepository).save(existing);
    }

    @Test
    void updateTopic_shouldThrowException_whenIdIsNull() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming");
        Topic topic = new Topic("Core Java", studyArea);

        // Act & Assert
        assertThrows(InvalidTopicException.class, () -> topicService.updateTopic(null, topic));
    }

    @Test
    void updateTopic_shouldThrowException_whenIdIsZero() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming");
        Topic topic = new Topic("Core Java", studyArea);

        // Act & Assert
        assertThrows(InvalidTopicException.class, () -> topicService.updateTopic(0L, topic));
    }

    @Test
    void updateTopic_shouldThrowException_whenIdIsNegative() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming");
        Topic topic = new Topic("Core Java", studyArea);

        // Act & Assert
        assertThrows(InvalidTopicException.class, () -> topicService.updateTopic(-1L, topic));
    }

    @Test
    void updateTopic_shouldThrowException_whenTopicNotFound() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming");
        Topic topic = new Topic("Core Java", studyArea);

        when(topicRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TopicNotFoundException.class, () -> topicService.updateTopic(999L, topic));
    }

    @Test
    void updateTopic_shouldThrowException_whenNewNameAlreadyExists() {
        // Arrange
        StudyArea studyArea = new StudyArea("Java", "Java programming");
        Topic existing = new Topic("Old Name", studyArea);
        existing.setId(1L);

        Topic updated = new Topic("Duplicate Name", studyArea);

        when(topicRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(topicRepository.existsByName("Duplicate Name")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateTopicException.class, () -> topicService.updateTopic(1L, updated));
        verify(topicRepository, never()).save(any());
    }
}
