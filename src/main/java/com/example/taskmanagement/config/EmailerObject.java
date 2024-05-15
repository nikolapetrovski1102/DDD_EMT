package com.example.taskmanagement.config;

import lombok.Data;

@Data
public class EmailerObject {

    private String subject;
    private String body;
    private String to;

    public EmailerObject(String subject, String body, String to) {
        this.subject = subject;
        this.body = body;
        this.to = to;
    }

}
