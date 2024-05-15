package com.example.taskmanagement.models;

public enum TaskStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    OVERDUE("Overdue");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}