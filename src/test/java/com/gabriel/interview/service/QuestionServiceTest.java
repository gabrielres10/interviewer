package com.gabriel.interview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gabriel.interview.exception.DuplicateQuestionException;
import com.gabriel.interview.exception.InvalidQuestionException;
import com.gabriel.interview.exception.QuestionNotFoundException;
import com.gabriel.interview.model.Question;
import com.gabriel.interview.repository.QuestionRepository;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @Test
    void createQuestion_shouldSaveQuestion_whenNoDuplicateExists() {
        Question question = new Question();
        question.setQuestionText("What is Java?");

        when(questionRepository.existsByQuestionText("What is Java?"))
                .thenReturn(false);
        when(questionRepository.save(question))
                .thenReturn(question);

        Question result = questionService.createQuestion(question);

        assertNotNull(result, "Created question should not be null");
        verify(questionRepository).save(question);
    }

    @Test
    void createQuestion_shouldThrowException_whenDuplicateExists() {
        Question question = new Question();
        question.setQuestionText("What is Java?");

        when(questionRepository.existsByQuestionText("What is Java?"))
                .thenReturn(true);

        assertThrows(DuplicateQuestionException.class,
                () -> questionService.createQuestion(question));

        verify(questionRepository, never()).save(any());
    }


    @Test
    void getQuestionById_shouldThrowException_whenIdIsInvalid() {
        assertThrows(InvalidQuestionException.class,
                () -> questionService.getQuestionById(0L));
    }

    @Test
    void getQuestionById_shouldThrowException_whenQuestionNotFound() {
        when(questionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(QuestionNotFoundException.class,
                () -> questionService.getQuestionById(1L));
    }

    @Test
    void getQuestionById_shouldReturnQuestion_whenExists() {
        Question question = new Question();
        question.setQuestionText("What is Java?");

        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(question));

        Question result = questionService.getQuestionById(1L);

        assertEquals("What is Java?", result.getQuestionText(), "Question text should match the one retrieved from repository");
    }

    @Test
    void updateQuestion_shouldUpdateQuestion_whenValid() {
        Question existing = new Question();
        existing.setQuestionText("Old text");

        Question updated = new Question();
        updated.setQuestionText("New text");

        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(questionRepository.existsByQuestionText("New text"))
                .thenReturn(false);
        when(questionRepository.save(existing))
                .thenReturn(existing);

        Question result = questionService.updateQuestion(1L, updated);

        assertEquals("New text", result.getQuestionText(), "Question text should be updated to 'New text'");
    }

    @Test
    void deleteQuestion_shouldDelete_whenQuestionExists() {
        Question question = new Question();

        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(question));

        questionService.deleteQuestion(1L);

        verify(questionRepository).delete(question);
    }

}
