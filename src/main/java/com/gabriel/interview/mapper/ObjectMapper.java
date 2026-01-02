package com.gabriel.interview.mapper;

import com.gabriel.interview.dto.QuestionCreateRequest;
import com.gabriel.interview.dto.QuestionResponse;
import com.gabriel.interview.dto.TopicResponse;
import com.gabriel.interview.model.Question;
import com.gabriel.interview.model.Topic;
import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between entities and DTOs.
 * Provides methods to transform domain objects to data transfer objects and vice versa.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
@Component
public class ObjectMapper {
    
    /**
     * Converts a QuestionCreateRequest DTO to a Question entity.
     * 
     * @param dto The question creation request DTO
     * @param topic The topic to associate with the question
     * @return A new Question entity
     */
    public Question toQuestionEntity(QuestionCreateRequest dto, Topic topic) {
        Question question = new Question(
            dto.getQuestionText(),
            dto.getAnswer(),
            dto.getDifficulty(),
            topic
        );

        return question;
    }

    /**
     * Converts a Question entity to a QuestionResponse DTO.
     * 
     * @param question The question entity to convert
     * @return QuestionResponse DTO with question data
     */
    public QuestionResponse toQuestionResponse(Question question) {
        QuestionResponse response = new QuestionResponse();
        
        response.setId(question.getId());
        response.setQuestionText(question.getQuestionText());
        response.setAnswer(question.getAnswer());
        response.setDifficulty(question.getDifficulty().name());

        response.setTopic(toTopicResponse(question.getTopic()));
        return response;
    }

    /**
     * Converts a Topic entity to a TopicResponse DTO.
     * 
     * @param topic The topic entity to convert
     * @return TopicResponse DTO with topic data
     */
    public TopicResponse toTopicResponse(Topic topic) {
        return new TopicResponse(
            topic.getId(),
            topic.getName()
        );
    }
}
