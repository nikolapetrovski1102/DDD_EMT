package com.example.taskmanagement.event;

import com.example.taskmanagement.config.Emailer;
import com.example.taskmanagement.config.EmailerObject;
import com.example.taskmanagement.models.Task;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TaskEvent extends ApplicationEvent {

   private final String subject;
   private final String body;
   private final String to;

    public TaskEvent(EmailerObject source, String subject, String body, String to){
        super(source);
        this.subject = subject;
        this.body = body;
        this.to = to;
    }

}
