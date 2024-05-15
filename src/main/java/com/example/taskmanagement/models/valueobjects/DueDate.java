package com.example.taskmanagement.models.valueobjects;

import java.time.LocalDateTime;

import com.example.taskmanagement.models.domain.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class DueDate implements ValueObject {
    private final LocalDateTime date;

    protected DueDate() {
        this.date = null;
    }

    public DueDate(LocalDateTime date){
        if (!date.isBefore(LocalDateTime.now())){
            this.date = date;
        }
        else
            this.date = null;
    }

}