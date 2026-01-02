package com.gabriel.interview.service;

import com.gabriel.interview.exception.DuplicateQuestionException;
import com.gabriel.interview.exception.InvalidQuestionException;
import com.gabriel.interview.exception.QuestionNotFoundException;
import com.gabriel.interview.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import com.gabriel.interview.model.Question;

import java.util.List;

/**
 * Service class for managing interview questions.
 * Handles business logic for question operations including creation, retrieval, update, and deletion.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    /**
     * Constructor for QuestionService with dependency injection.
     * 
     * @param qr QuestionRepository for database operations
     */
    public QuestionService(QuestionRepository qr) {
        this.questionRepository = qr;
    }


    /**
     * Creates a new question in the database.
     * Validates that a question with the same text does not already exist.
     * 
     * @param question The question entity to create
     * @return The created question with generated ID
     * @throws DuplicateQuestionException if a question with the same text already exists
     */
    public Question createQuestion(Question question) {
        
        // Verificar si ya existe una pregunta con el mismo texto
        if (questionRepository.existsByQuestionText(question.getQuestionText())) {
            throw new DuplicateQuestionException("A question with the same text already exists");
        }
        
        return questionRepository.save(question);
    }

    /**
     * Retrieves a question by its ID.
     * 
     * @param id The ID of the question to retrieve
     * @return The question entity
     * @throws InvalidQuestionException if the ID is null or non-positive
     * @throws QuestionNotFoundException if no question exists with the given ID
     */
    public Question getQuestionById(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidQuestionException("Question ID must be a positive number");
        }
        
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
    }


    /**
     * Retrieves all questions from the database.
     * 
     * @return List of all question entities
     */
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    /**
     * Updates an existing question.
     * Validates that the new question text does not duplicate an existing question.
     * 
     * @param id The ID of the question to update
     * @param updatedQuestion The question entity with updated information
     * @return The updated question entity
     * @throws InvalidQuestionException if the ID is null or non-positive
     * @throws QuestionNotFoundException if no question exists with the given ID
     * @throws DuplicateQuestionException if the updated text matches an existing question
     */
    public Question updateQuestion(Long id, Question updatedQuestion) {
        if (id == null || id <= 0) {
            throw new InvalidQuestionException("Question ID must be a positive number");
        }
        
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        
        // Verificar duplicados solo si el texto cambió
        if (!existingQuestion.getQuestionText().equals(updatedQuestion.getQuestionText())) {
            if (questionRepository.existsByQuestionText(updatedQuestion.getQuestionText())) {
                throw new DuplicateQuestionException("A question with the same text already exists");
            }
        }
        
        existingQuestion.setQuestionText(updatedQuestion.getQuestionText());
        existingQuestion.setAnswer(updatedQuestion.getAnswer());
        existingQuestion.setDifficulty(updatedQuestion.getDifficulty());
        existingQuestion.setTopic(updatedQuestion.getTopic());
        
        return questionRepository.save(existingQuestion);
    }


    /**
     * Deletes a question from the database.
     * 
     * @param id The ID of the question to delete
     * @throws InvalidQuestionException if the ID is null or non-positive
     * @throws QuestionNotFoundException if no question exists with the given ID
     */
    public void deleteQuestion(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidQuestionException("Question ID must be a positive number");
        }
        
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        
        // Aquí se podrían agregar validaciones de reglas de negocio
        // Por ejemplo, no permitir eliminar preguntas que están siendo usadas en algún examen
        // if (question.isBeingUsed()) {
        //     throw new QuestionCannotBeDeletedException("Cannot delete question that is being used in an exam");
        // }
        
        questionRepository.delete(question);
    }
}
