package com.example.taskmanagement.listener;

import com.example.taskmanagement.config.Emailer;
import com.example.taskmanagement.event.TaskEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TaskEventListener {

    Emailer emailer;

    public TaskEventListener(Emailer emailer) {
        this.emailer = emailer;
    }

    @EventListener
    public void onBooksListed(TaskEvent event){
        emailer.sendEmail(event.getTo(), event.getSubject(), event.getBody());
    }

}
