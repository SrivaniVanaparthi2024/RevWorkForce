package com.revworkforce.model;

import java.util.Date;

public class Goal {

    private int goalId;
    private int empId;
    private String description;
    private Date deadline;
    private String priority;
    private int progressPercentage;
    private String status;

    public Goal() {
    }

    public Goal(int goalId, int empId, String description,
                Date deadline, String priority,
                int progressPercentage, String status) {
        this.goalId = goalId;
        this.empId = empId;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.progressPercentage = progressPercentage;
        this.status = status;
    }

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public int getempId() {
        return empId;
    }

    public void setempId(int empId) {
        this.empId = empId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

