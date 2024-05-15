package com.example.taskmanagement.models.valueobjects;

import com.example.taskmanagement.models.domain.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class TaskDescription implements ValueObject {
    private final String description;

    protected TaskDescription() {
        this.description = "";
    }

    public TaskDescription(String description)
    {
        this.description = description;
    }
}