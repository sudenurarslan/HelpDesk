package com.helpdesk.model;

import java.sql.Timestamp;

public class Ticket {
    private int id;
    private int userId;
    private String title;
    private String description;
    private String status;
    private String priority;
    private Timestamp createdAt;

    public Ticket(int id, int userId, String title, String description,
                  String status, String priority, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
    }

    public Ticket(int userId, String title, String description,
                  String status, String priority) {
        this(-1, userId, title, description, status, priority, null);
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void setStatus(String status) { this.status = status; }
    public void setPriority(String priority) { this.priority = priority; }
}
