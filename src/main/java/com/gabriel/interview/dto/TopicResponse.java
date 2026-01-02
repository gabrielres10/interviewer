package com.gabriel.interview.dto;

/**
 * Data Transfer Object representing a topic response.
 * Used to return topic data to clients.
 * 
 * @author Gabriel
 * @version 1.0
 * @since 2026-01-02
 */
public class TopicResponse {
    private Long id;
    private String name;

    public TopicResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
